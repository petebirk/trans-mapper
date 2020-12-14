package org.backbase.handler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.backbase.model.Transaction;
import org.backbase.util.ThreadLocalHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

public class ByTypeTransactionDeserializer extends StdDeserializer<List<Transaction>> { 

	private static final long serialVersionUID = -1633286323316573808L;
	private static Logger logger = LoggerFactory.getLogger(ByTypeTransactionDeserializer.class);

	public ByTypeTransactionDeserializer() { 
        this(null); 
    } 

    public ByTypeTransactionDeserializer(Class<?> vc) { 
        super(vc); 
    }

    @Override
    public List<Transaction> deserialize(JsonParser jp, DeserializationContext ctxt) 
      throws IOException, JsonProcessingException {
    	ObjectCodec oc = jp.getCodec();
        JsonNode node = oc.readTree(jp);
        List<Transaction> transactionList = new ArrayList<Transaction>();
        
        if (node.isArray()) {
            for (JsonNode elementNode : node) {
            	Transaction transaction = parseTransactionObject(elementNode);
            	if (transaction != null) transactionList.add(transaction);
            }
        } else {
        	Transaction transaction = parseTransactionObject(node);
        	if (transaction != null) transactionList.add(transaction);
        }
        
		if (logger.isDebugEnabled()) logger.debug("Returning " + transactionList.size() + " mapped and filtered transactions.");
        return transactionList;
    }
    
    
    private Transaction parseTransactionObject (JsonNode node) {
    	Transaction transaction = new Transaction();
    	String requestType = ThreadLocalHelper.get();
    	
		JsonNode details = node.get("details");
		if (details != null) {
			String currentRequestType = details.get("type").asText();
			transaction.setTransactionType(currentRequestType);
			// This returns quickly if the type doesn't match the requested type.
			if (requestType == null || requestType.contentEquals("") ||
				!requestType.contentEquals(currentRequestType)) {
				return null;
			}
			transaction.setDescription(details.get("description").asText());
    		JsonNode value = details.get("value");
			if (value != null) {
				String amount = value.get("amount").asText();
				String currency = value.get("currency").asText();
				transaction.setInstructedAmount(amount);
				transaction.setInstructedCurrency(currency);
				transaction.setTransactionAmount(amount);
				transaction.setTransactionCurrency(currency);
			}
		}
    	transaction.setId(node.get("id").asText());
    	JsonNode this_account = node.get("this_account");
    	if (this_account != null) {
    		transaction.setAccountId(this_account.get("id").asText());
    	}
    	JsonNode other_account = node.get("other_account");
    	if (other_account != null) {
    		transaction.setCounterpartyAccount(other_account.get("number").asText());
    		JsonNode holder = other_account.get("holder");
    		if (holder != null) {
    			transaction.setCounterpartyName(holder.get("name").asText());
    		}
    		JsonNode metadata = other_account.get("metadata");
    		if (metadata != null) {
    			transaction.setCounterPartyLogoPath(metadata.get("image_URL").asText());
    		}
    	}
		return transaction;
    }
}
