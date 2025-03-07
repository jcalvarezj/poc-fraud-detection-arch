import os
import json
import requests

from pydantic import BaseModel
from requests.exceptions import ConnectionError

from models import Transaction


def model_dump_fix_keys(model: BaseModel) -> dict:
    """
    Converts all the input model keys to upper case and dumps it as
    a dictionary

    Args:
        - model (pydantic.BaseModel): The input model to fit and dump
    """
    return {
        key.upper(): value
        for key, value in model.model_dump().items()
    }

def extract_transaction_data(message: dict) -> dict:
    """
    Transformation that returns a TransactionResult dictionary dump from an
    input message that corresponds to a Transaction

    Args:
        - message (dict): The input message to transform
    Raises:
        - Exception: Any uncaught exception
    """
    message["evaluation"] = "fraudulent" if message.pop("is_fraudulent", None) else None
    transaction = Transaction.model_validate(message)
    transaction_result = transaction.to_transaction_result()
    print(f"Sending transaction data!")
    print(transaction_result)
    return model_dump_fix_keys(transaction_result)

def extract_user_data(message: dict, is_sender: bool = False) -> dict:
    """
    Transformation that returns a User dictionary dump from an input message
    that corresponds to a Transaction

    Args:
        - message (dict): The input message to transform
    Raises:
        - Exception: Any uncaught exception
    """
    transaction = Transaction.model_validate(message)
    user = transaction.get_user_data(is_sender)
    print(f"Sending {'sender' if is_sender else 'receiver'} user data!")
    print(user)
    return model_dump_fix_keys(user)

def identify_fraudulence(message: dict) -> dict:
    """
    Transformation that calls the Fraud Validator API to identify
    whether the input message corresponds to a fraudulent transaction
    and appends the findings in the "is_fraudulent_transaction" property

    Args:
        - message (dict): The input message to transform
    Raises:
        - requests.exceptions.ConnectionError: When it is not possible to
        reach the Fraud Validator API
        - Exception: Any uncaught exception
    """
    payload = {
        "transaction_id": message["transaction_id"],
        "sender_bank_account": message["sender_bank_account"],
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
        print(f"Could not connect to Fraud Validator API. Stopping...")
        raise e
    except Exception as e:
        print(f"An unexpected error occurred when identifying fraudulence {e}")
        raise e
