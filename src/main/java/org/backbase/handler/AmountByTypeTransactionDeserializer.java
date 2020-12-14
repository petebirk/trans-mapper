package org.backbase.handler;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

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

public class AmountByTypeTransactionDeserializer extends StdDeserializer<String> { 

	private static final long serialVersionUID = -1633286323316573808L;
	private static Logger logger = LoggerFactory.getLogger(AmountByTypeTransactionDeserializer.class);

	public AmountByTypeTransactionDeserializer() { 
        this(null); 
    } 

    public AmountByTypeTransactionDeserializer(Class<?> vc) { 
        super(vc); 
    }

    Map<String,BigDecimal> currencyMapper = new HashMap<String,BigDecimal>();

    @Override
    public String deserialize(JsonParser jp, DeserializationContext ctxt) 
      throws IOException, JsonProcessingException {
    	ObjectCodec oc = jp.getCodec();
        JsonNode node = oc.readTree(jp);
        
        if (node.isArray()) {
            for (JsonNode elementNode : node) {
				if (logger.isDebugEnabled()) logger.debug("Currently processing node: " + elementNode);
            	parseTransactionObject(elementNode);
            }
        } else {
        	parseTransactionObject(node);
        }
        
	    String json = "";
	    // Return JSON of currency : sum(amount).
	    if (currencyMapper.size() > 0) {
		    StringBuilder builder = new StringBuilder("{");
		    for (Map.Entry<String,BigDecimal> value : currencyMapper.entrySet()) {
		    	builder.append('"').append(value.getKey()).append('"').append(":").append('"').append(value.getValue()).append('"').append(",");
		    }
		    json = builder.toString();
		    if (json.length() > 1) json = json.substring(0, json.length()-1)+"}";
	    }
	    
	    return json;
    }
    
    
    private void parseTransactionObject (JsonNode node) {
    	Transaction transaction = new Transaction();
    	String requestType = ThreadLocalHelper.get();
    	
    	
		JsonNode details = node.get("details");
		if (details != null) {
			String currentRequestType = details.get("type").asText();
			transaction.setTransactionType(currentRequestType);
			// This returns quickly if the type doesn't match the requested type.
			if (requestType == null || requestType.contentEquals("") ||
				!requestType.contentEquals(currentRequestType)) {
				return;
			}
			transaction.setDescription(details.get("description").asText());
    		JsonNode value = details.get("value");
			if (value != null) {
				String amount = value.get("amount").asText();
				String currency = value.get("currency").asText();
				
				if (amount != null && currency != null) {
		    		BigDecimal newValue = new BigDecimal(amount);
		    		BigDecimal currentValue = currencyMapper.getOrDefault(currency, new BigDecimal(0));
		    		currencyMapper.put(currency, currentValue.add(newValue));
				}
			}
		}
    }
}
