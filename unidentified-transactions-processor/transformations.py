import os
import json
import requests

from pydantic import BaseModel
from requests.exceptions import ConnectionError

from models import Transaction


def model_dump_fix_keys(model: BaseModel):
    return {
        key.upper(): value
        for key, value in model.model_dump().items()
    }

def extract_transaction_data(message: dict):
    message.pop("is_fraudulent", None)
    transaction = Transaction.model_validate(message)
    transaction_result = transaction.to_transaction_result()
    print(f"Sending transaction data!")
    print(transaction_result)
    return model_dump_fix_keys(transaction_result)

def extract_user_data(message: dict, is_sender: bool = False):
    transaction = Transaction.model_validate(message)
    user = transaction.get_user_data(is_sender)
    print(f"Sending {'sender' if is_sender else 'receiver'} user data!")
    print(user)
    return model_dump_fix_keys(user)

def identify_fraudulence(message: dict):
    payload = {
        "transaction_id": message["transaction_id"],
        "sender_bank_account": message["transaction_id"],
        "receiver_details": json.dumps(message["receiver_details"]),
        "sender_bank": message["sender_bank"],
        "amount": message["amount"]
    }
    try:
        response = requests.post(os.getenv("FRAUD_VALIDATOR_URL"), json=payload)
        message["is_fraudulent"] = response.json()["is_fraudulent_transaction"]
        print(f"Transaction {message['transaction_id']} is fraudulent? - {message['is_fraudulent']}")
        return message
    except ConnectionError as e:
        raise Exception(f"Could not connect to Fraud Validator API. Stopping... {e}")