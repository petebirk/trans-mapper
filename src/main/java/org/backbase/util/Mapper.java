package org.backbase.util;

import org.backbase.model.Transaction;

/**
 * Helper class for mapping Transaction objects.
 * @author peterbirk
 *
 */
public class Mapper {
	
	public static Transaction mapTransaction (org.openbank.model.Transaction openbanktrans) {
		if (openbanktrans == null) return null;
		
		Transaction backbasetrans = new Transaction();
		backbasetrans.setId(openbanktrans.getId());
		backbasetrans.setAccountId(openbanktrans.getThis_account()!=null?
					openbanktrans.getThis_account().getId() : null);
		backbasetrans.setCounterpartyAccount(openbanktrans.getOther_account()!=null?
					openbanktrans.getOther_account().getNumber() : null);
		backbasetrans.setCounterpartyName(openbanktrans.getOther_account()!=null&&
					openbanktrans.getOther_account().getHolder() != null ?
					openbanktrans.getOther_account().getHolder().getName() : null);
		backbasetrans.setCounterPartyLogoPath(openbanktrans.getOther_account()!=null && 
					openbanktrans.getOther_account().getMetadata()!=null ?
					openbanktrans.getOther_account().getMetadata().getImage_URL() : null);
		backbasetrans.setInstructedAmount(openbanktrans.getDetails() != null &&
					openbanktrans.getDetails().getValue() != null ?
					openbanktrans.getDetails().getValue().getAmount() : null);
		backbasetrans.setInstructedCurrency(openbanktrans.getDetails() != null &&
					openbanktrans.getDetails().getValue() != null ?
					openbanktrans.getDetails().getValue().getCurrency() : null);
		backbasetrans.setTransactionAmount(openbanktrans.getDetails() != null &&
					openbanktrans.getDetails().getValue() != null ?
					openbanktrans.getDetails().getValue().getAmount() : null);
		backbasetrans.setTransactionCurrency(openbanktrans.getDetails() != null &&
					openbanktrans.getDetails().getValue() != null ?
					openbanktrans.getDetails().getValue().getCurrency() : null);
		backbasetrans.setTransactionType(openbanktrans.getDetails()!=null ?
					openbanktrans.getDetails().getType() : null);
		backbasetrans.setDescription(openbanktrans.getDetails()!=null ? 
					openbanktrans.getDetails().getDescription() : null);
		
		return backbasetrans;
	}
}
