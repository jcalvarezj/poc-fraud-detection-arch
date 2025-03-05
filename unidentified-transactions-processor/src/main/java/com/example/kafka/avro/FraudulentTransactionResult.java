/**
 * Autogenerated by Avro
 *
 * DO NOT EDIT DIRECTLY
 */
package com.example.kafka.avro;

import org.apache.avro.specific.SpecificData;
import org.apache.avro.util.Utf8;
import org.apache.avro.message.BinaryMessageEncoder;
import org.apache.avro.message.BinaryMessageDecoder;
import org.apache.avro.message.SchemaStore;

@org.apache.avro.specific.AvroGenerated
public class FraudulentTransactionResult extends org.apache.avro.specific.SpecificRecordBase implements org.apache.avro.specific.SpecificRecord {
  private static final long serialVersionUID = 2541126099141521483L;


  public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"FraudulentTransactionResult\",\"fields\":[{\"name\":\"transaction_id\",\"type\":\"string\"},{\"name\":\"sender_bank_account\",\"type\":\"string\"},{\"name\":\"sender_id\",\"type\":\"string\"},{\"name\":\"receiver_bank_account\",\"type\":\"string\"},{\"name\":\"receiver_id\",\"type\":\"string\"},{\"name\":\"amount\",\"type\":\"float\"},{\"name\":\"status\",\"type\":\"string\"},{\"name\":\"evaluation\",\"type\":\"string\"},{\"name\":\"transfer_date\",\"type\":\"string\"},{\"name\":\"sender_bank\",\"type\":\"string\"}]}");
  public static org.apache.avro.Schema getClassSchema() { return SCHEMA$; }

  private static final SpecificData MODEL$ = new SpecificData();

  private static final BinaryMessageEncoder<FraudulentTransactionResult> ENCODER =
      new BinaryMessageEncoder<>(MODEL$, SCHEMA$);

  private static final BinaryMessageDecoder<FraudulentTransactionResult> DECODER =
      new BinaryMessageDecoder<>(MODEL$, SCHEMA$);

  /**
   * Return the BinaryMessageEncoder instance used by this class.
   * @return the message encoder used by this class
   */
  public static BinaryMessageEncoder<FraudulentTransactionResult> getEncoder() {
    return ENCODER;
  }

  /**
   * Return the BinaryMessageDecoder instance used by this class.
   * @return the message decoder used by this class
   */
  public static BinaryMessageDecoder<FraudulentTransactionResult> getDecoder() {
    return DECODER;
  }

  /**
   * Create a new BinaryMessageDecoder instance for this class that uses the specified {@link SchemaStore}.
   * @param resolver a {@link SchemaStore} used to find schemas by fingerprint
   * @return a BinaryMessageDecoder instance for this class backed by the given SchemaStore
   */
  public static BinaryMessageDecoder<FraudulentTransactionResult> createDecoder(SchemaStore resolver) {
    return new BinaryMessageDecoder<>(MODEL$, SCHEMA$, resolver);
  }

  /**
   * Serializes this FraudulentTransactionResult to a ByteBuffer.
   * @return a buffer holding the serialized data for this instance
   * @throws java.io.IOException if this instance could not be serialized
   */
  public java.nio.ByteBuffer toByteBuffer() throws java.io.IOException {
    return ENCODER.encode(this);
  }

  /**
   * Deserializes a FraudulentTransactionResult from a ByteBuffer.
   * @param b a byte buffer holding serialized data for an instance of this class
   * @return a FraudulentTransactionResult instance decoded from the given buffer
   * @throws java.io.IOException if the given bytes could not be deserialized into an instance of this class
   */
  public static FraudulentTransactionResult fromByteBuffer(
      java.nio.ByteBuffer b) throws java.io.IOException {
    return DECODER.decode(b);
  }

  private java.lang.CharSequence transaction_id;
  private java.lang.CharSequence sender_bank_account;
  private java.lang.CharSequence sender_id;
  private java.lang.CharSequence receiver_bank_account;
  private java.lang.CharSequence receiver_id;
  private float amount;
  private java.lang.CharSequence status;
  private java.lang.CharSequence evaluation;
  private java.lang.CharSequence transfer_date;
  private java.lang.CharSequence sender_bank;

  /**
   * Default constructor.  Note that this does not initialize fields
   * to their default values from the schema.  If that is desired then
   * one should use <code>newBuilder()</code>.
   */
  public FraudulentTransactionResult() {}

  /**
   * All-args constructor.
   * @param transaction_id The new value for transaction_id
   * @param sender_bank_account The new value for sender_bank_account
   * @param sender_id The new value for sender_id
   * @param receiver_bank_account The new value for receiver_bank_account
   * @param receiver_id The new value for receiver_id
   * @param amount The new value for amount
   * @param status The new value for status
   * @param evaluation The new value for evaluation
   * @param transfer_date The new value for transfer_date
   * @param sender_bank The new value for sender_bank
   */
  public FraudulentTransactionResult(java.lang.CharSequence transaction_id, java.lang.CharSequence sender_bank_account, java.lang.CharSequence sender_id, java.lang.CharSequence receiver_bank_account, java.lang.CharSequence receiver_id, java.lang.Float amount, java.lang.CharSequence status, java.lang.CharSequence evaluation, java.lang.CharSequence transfer_date, java.lang.CharSequence sender_bank) {
    this.transaction_id = transaction_id;
    this.sender_bank_account = sender_bank_account;
    this.sender_id = sender_id;
    this.receiver_bank_account = receiver_bank_account;
    this.receiver_id = receiver_id;
    this.amount = amount;
    this.status = status;
    this.evaluation = evaluation;
    this.transfer_date = transfer_date;
    this.sender_bank = sender_bank;
  }

  @Override
  public org.apache.avro.specific.SpecificData getSpecificData() { return MODEL$; }

  @Override
  public org.apache.avro.Schema getSchema() { return SCHEMA$; }

  // Used by DatumWriter.  Applications should not call.
  @Override
  public java.lang.Object get(int field$) {
    switch (field$) {
    case 0: return transaction_id;
    case 1: return sender_bank_account;
    case 2: return sender_id;
    case 3: return receiver_bank_account;
    case 4: return receiver_id;
    case 5: return amount;
    case 6: return status;
    case 7: return evaluation;
    case 8: return transfer_date;
    case 9: return sender_bank;
    default: throw new IndexOutOfBoundsException("Invalid index: " + field$);
    }
  }

  // Used by DatumReader.  Applications should not call.
  @Override
  @SuppressWarnings(value="unchecked")
  public void put(int field$, java.lang.Object value$) {
    switch (field$) {
    case 0: transaction_id = (java.lang.CharSequence)value$; break;
    case 1: sender_bank_account = (java.lang.CharSequence)value$; break;
    case 2: sender_id = (java.lang.CharSequence)value$; break;
    case 3: receiver_bank_account = (java.lang.CharSequence)value$; break;
    case 4: receiver_id = (java.lang.CharSequence)value$; break;
    case 5: amount = (java.lang.Float)value$; break;
    case 6: status = (java.lang.CharSequence)value$; break;
    case 7: evaluation = (java.lang.CharSequence)value$; break;
    case 8: transfer_date = (java.lang.CharSequence)value$; break;
    case 9: sender_bank = (java.lang.CharSequence)value$; break;
    default: throw new IndexOutOfBoundsException("Invalid index: " + field$);
    }
  }

  /**
   * Gets the value of the 'transaction_id' field.
   * @return The value of the 'transaction_id' field.
   */
  public java.lang.CharSequence getTransactionId() {
    return transaction_id;
  }


  /**
   * Sets the value of the 'transaction_id' field.
   * @param value the value to set.
   */
  public void setTransactionId(java.lang.CharSequence value) {
    this.transaction_id = value;
  }

  /**
   * Gets the value of the 'sender_bank_account' field.
   * @return The value of the 'sender_bank_account' field.
   */
  public java.lang.CharSequence getSenderBankAccount() {
    return sender_bank_account;
  }


  /**
   * Sets the value of the 'sender_bank_account' field.
   * @param value the value to set.
   */
  public void setSenderBankAccount(java.lang.CharSequence value) {
    this.sender_bank_account = value;
  }

  /**
   * Gets the value of the 'sender_id' field.
   * @return The value of the 'sender_id' field.
   */
  public java.lang.CharSequence getSenderId() {
    return sender_id;
  }


  /**
   * Sets the value of the 'sender_id' field.
   * @param value the value to set.
   */
  public void setSenderId(java.lang.CharSequence value) {
    this.sender_id = value;
  }

  /**
   * Gets the value of the 'receiver_bank_account' field.
   * @return The value of the 'receiver_bank_account' field.
   */
  public java.lang.CharSequence getReceiverBankAccount() {
    return receiver_bank_account;
  }


  /**
   * Sets the value of the 'receiver_bank_account' field.
   * @param value the value to set.
   */
  public void setReceiverBankAccount(java.lang.CharSequence value) {
    this.receiver_bank_account = value;
  }

  /**
   * Gets the value of the 'receiver_id' field.
   * @return The value of the 'receiver_id' field.
   */
  public java.lang.CharSequence getReceiverId() {
    return receiver_id;
  }


  /**
   * Sets the value of the 'receiver_id' field.
   * @param value the value to set.
   */
  public void setReceiverId(java.lang.CharSequence value) {
    this.receiver_id = value;
  }

  /**
   * Gets the value of the 'amount' field.
   * @return The value of the 'amount' field.
   */
  public float getAmount() {
    return amount;
  }


  /**
   * Sets the value of the 'amount' field.
   * @param value the value to set.
   */
  public void setAmount(float value) {
    this.amount = value;
  }

  /**
   * Gets the value of the 'status' field.
   * @return The value of the 'status' field.
   */
  public java.lang.CharSequence getStatus() {
    return status;
  }


  /**
   * Sets the value of the 'status' field.
   * @param value the value to set.
   */
  public void setStatus(java.lang.CharSequence value) {
    this.status = value;
  }

  /**
   * Gets the value of the 'evaluation' field.
   * @return The value of the 'evaluation' field.
   */
  public java.lang.CharSequence getEvaluation() {
    return evaluation;
  }


  /**
   * Sets the value of the 'evaluation' field.
   * @param value the value to set.
   */
  public void setEvaluation(java.lang.CharSequence value) {
    this.evaluation = value;
  }

  /**
   * Gets the value of the 'transfer_date' field.
   * @return The value of the 'transfer_date' field.
   */
  public java.lang.CharSequence getTransferDate() {
    return transfer_date;
  }


  /**
   * Sets the value of the 'transfer_date' field.
   * @param value the value to set.
   */
  public void setTransferDate(java.lang.CharSequence value) {
    this.transfer_date = value;
  }

  /**
   * Gets the value of the 'sender_bank' field.
   * @return The value of the 'sender_bank' field.
   */
  public java.lang.CharSequence getSenderBank() {
    return sender_bank;
  }


  /**
   * Sets the value of the 'sender_bank' field.
   * @param value the value to set.
   */
  public void setSenderBank(java.lang.CharSequence value) {
    this.sender_bank = value;
  }

  /**
   * Creates a new FraudulentTransactionResult RecordBuilder.
   * @return A new FraudulentTransactionResult RecordBuilder
   */
  public static FraudulentTransactionResult.Builder newBuilder() {
    return new FraudulentTransactionResult.Builder();
  }

  /**
   * Creates a new FraudulentTransactionResult RecordBuilder by copying an existing Builder.
   * @param other The existing builder to copy.
   * @return A new FraudulentTransactionResult RecordBuilder
   */
  public static FraudulentTransactionResult.Builder newBuilder(FraudulentTransactionResult.Builder other) {
    if (other == null) {
      return new FraudulentTransactionResult.Builder();
    } else {
      return new FraudulentTransactionResult.Builder(other);
    }
  }

  /**
   * Creates a new FraudulentTransactionResult RecordBuilder by copying an existing FraudulentTransactionResult instance.
   * @param other The existing instance to copy.
   * @return A new FraudulentTransactionResult RecordBuilder
   */
  public static FraudulentTransactionResult.Builder newBuilder(FraudulentTransactionResult other) {
    if (other == null) {
      return new FraudulentTransactionResult.Builder();
    } else {
      return new FraudulentTransactionResult.Builder(other);
    }
  }

  /**
   * RecordBuilder for FraudulentTransactionResult instances.
   */
  @org.apache.avro.specific.AvroGenerated
  public static class Builder extends org.apache.avro.specific.SpecificRecordBuilderBase<FraudulentTransactionResult>
    implements org.apache.avro.data.RecordBuilder<FraudulentTransactionResult> {

    private java.lang.CharSequence transaction_id;
    private java.lang.CharSequence sender_bank_account;
    private java.lang.CharSequence sender_id;
    private java.lang.CharSequence receiver_bank_account;
    private java.lang.CharSequence receiver_id;
    private float amount;
    private java.lang.CharSequence status;
    private java.lang.CharSequence evaluation;
    private java.lang.CharSequence transfer_date;
    private java.lang.CharSequence sender_bank;

    /** Creates a new Builder */
    private Builder() {
      super(SCHEMA$, MODEL$);
    }

    /**
     * Creates a Builder by copying an existing Builder.
     * @param other The existing Builder to copy.
     */
    private Builder(FraudulentTransactionResult.Builder other) {
      super(other);
      if (isValidValue(fields()[0], other.transaction_id)) {
        this.transaction_id = data().deepCopy(fields()[0].schema(), other.transaction_id);
        fieldSetFlags()[0] = other.fieldSetFlags()[0];
      }
      if (isValidValue(fields()[1], other.sender_bank_account)) {
        this.sender_bank_account = data().deepCopy(fields()[1].schema(), other.sender_bank_account);
        fieldSetFlags()[1] = other.fieldSetFlags()[1];
      }
      if (isValidValue(fields()[2], other.sender_id)) {
        this.sender_id = data().deepCopy(fields()[2].schema(), other.sender_id);
        fieldSetFlags()[2] = other.fieldSetFlags()[2];
      }
      if (isValidValue(fields()[3], other.receiver_bank_account)) {
        this.receiver_bank_account = data().deepCopy(fields()[3].schema(), other.receiver_bank_account);
        fieldSetFlags()[3] = other.fieldSetFlags()[3];
      }
      if (isValidValue(fields()[4], other.receiver_id)) {
        this.receiver_id = data().deepCopy(fields()[4].schema(), other.receiver_id);
        fieldSetFlags()[4] = other.fieldSetFlags()[4];
      }
      if (isValidValue(fields()[5], other.amount)) {
        this.amount = data().deepCopy(fields()[5].schema(), other.amount);
        fieldSetFlags()[5] = other.fieldSetFlags()[5];
      }
      if (isValidValue(fields()[6], other.status)) {
        this.status = data().deepCopy(fields()[6].schema(), other.status);
        fieldSetFlags()[6] = other.fieldSetFlags()[6];
      }
      if (isValidValue(fields()[7], other.evaluation)) {
        this.evaluation = data().deepCopy(fields()[7].schema(), other.evaluation);
        fieldSetFlags()[7] = other.fieldSetFlags()[7];
      }
      if (isValidValue(fields()[8], other.transfer_date)) {
        this.transfer_date = data().deepCopy(fields()[8].schema(), other.transfer_date);
        fieldSetFlags()[8] = other.fieldSetFlags()[8];
      }
      if (isValidValue(fields()[9], other.sender_bank)) {
        this.sender_bank = data().deepCopy(fields()[9].schema(), other.sender_bank);
        fieldSetFlags()[9] = other.fieldSetFlags()[9];
      }
    }

    /**
     * Creates a Builder by copying an existing FraudulentTransactionResult instance
     * @param other The existing instance to copy.
     */
    private Builder(FraudulentTransactionResult other) {
      super(SCHEMA$, MODEL$);
      if (isValidValue(fields()[0], other.transaction_id)) {
        this.transaction_id = data().deepCopy(fields()[0].schema(), other.transaction_id);
        fieldSetFlags()[0] = true;
      }
      if (isValidValue(fields()[1], other.sender_bank_account)) {
        this.sender_bank_account = data().deepCopy(fields()[1].schema(), other.sender_bank_account);
        fieldSetFlags()[1] = true;
      }
      if (isValidValue(fields()[2], other.sender_id)) {
        this.sender_id = data().deepCopy(fields()[2].schema(), other.sender_id);
        fieldSetFlags()[2] = true;
      }
      if (isValidValue(fields()[3], other.receiver_bank_account)) {
        this.receiver_bank_account = data().deepCopy(fields()[3].schema(), other.receiver_bank_account);
        fieldSetFlags()[3] = true;
      }
      if (isValidValue(fields()[4], other.receiver_id)) {
        this.receiver_id = data().deepCopy(fields()[4].schema(), other.receiver_id);
        fieldSetFlags()[4] = true;
      }
      if (isValidValue(fields()[5], other.amount)) {
        this.amount = data().deepCopy(fields()[5].schema(), other.amount);
        fieldSetFlags()[5] = true;
      }
      if (isValidValue(fields()[6], other.status)) {
        this.status = data().deepCopy(fields()[6].schema(), other.status);
        fieldSetFlags()[6] = true;
      }
      if (isValidValue(fields()[7], other.evaluation)) {
        this.evaluation = data().deepCopy(fields()[7].schema(), other.evaluation);
        fieldSetFlags()[7] = true;
      }
      if (isValidValue(fields()[8], other.transfer_date)) {
        this.transfer_date = data().deepCopy(fields()[8].schema(), other.transfer_date);
        fieldSetFlags()[8] = true;
      }
      if (isValidValue(fields()[9], other.sender_bank)) {
        this.sender_bank = data().deepCopy(fields()[9].schema(), other.sender_bank);
        fieldSetFlags()[9] = true;
      }
    }

    /**
      * Gets the value of the 'transaction_id' field.
      * @return The value.
      */
    public java.lang.CharSequence getTransactionId() {
      return transaction_id;
    }


    /**
      * Sets the value of the 'transaction_id' field.
      * @param value The value of 'transaction_id'.
      * @return This builder.
      */
    public FraudulentTransactionResult.Builder setTransactionId(java.lang.CharSequence value) {
      validate(fields()[0], value);
      this.transaction_id = value;
      fieldSetFlags()[0] = true;
      return this;
    }

    /**
      * Checks whether the 'transaction_id' field has been set.
      * @return True if the 'transaction_id' field has been set, false otherwise.
      */
    public boolean hasTransactionId() {
      return fieldSetFlags()[0];
    }


    /**
      * Clears the value of the 'transaction_id' field.
      * @return This builder.
      */
    public FraudulentTransactionResult.Builder clearTransactionId() {
      transaction_id = null;
      fieldSetFlags()[0] = false;
      return this;
    }

    /**
      * Gets the value of the 'sender_bank_account' field.
      * @return The value.
      */
    public java.lang.CharSequence getSenderBankAccount() {
      return sender_bank_account;
    }


    /**
      * Sets the value of the 'sender_bank_account' field.
      * @param value The value of 'sender_bank_account'.
      * @return This builder.
      */
    public FraudulentTransactionResult.Builder setSenderBankAccount(java.lang.CharSequence value) {
      validate(fields()[1], value);
      this.sender_bank_account = value;
      fieldSetFlags()[1] = true;
      return this;
    }

    /**
      * Checks whether the 'sender_bank_account' field has been set.
      * @return True if the 'sender_bank_account' field has been set, false otherwise.
      */
    public boolean hasSenderBankAccount() {
      return fieldSetFlags()[1];
    }


    /**
      * Clears the value of the 'sender_bank_account' field.
      * @return This builder.
      */
    public FraudulentTransactionResult.Builder clearSenderBankAccount() {
      sender_bank_account = null;
      fieldSetFlags()[1] = false;
      return this;
    }

    /**
      * Gets the value of the 'sender_id' field.
      * @return The value.
      */
    public java.lang.CharSequence getSenderId() {
      return sender_id;
    }


    /**
      * Sets the value of the 'sender_id' field.
      * @param value The value of 'sender_id'.
      * @return This builder.
      */
    public FraudulentTransactionResult.Builder setSenderId(java.lang.CharSequence value) {
      validate(fields()[2], value);
      this.sender_id = value;
      fieldSetFlags()[2] = true;
      return this;
    }

    /**
      * Checks whether the 'sender_id' field has been set.
      * @return True if the 'sender_id' field has been set, false otherwise.
      */
    public boolean hasSenderId() {
      return fieldSetFlags()[2];
    }


    /**
      * Clears the value of the 'sender_id' field.
      * @return This builder.
      */
    public FraudulentTransactionResult.Builder clearSenderId() {
      sender_id = null;
      fieldSetFlags()[2] = false;
      return this;
    }

    /**
      * Gets the value of the 'receiver_bank_account' field.
      * @return The value.
      */
    public java.lang.CharSequence getReceiverBankAccount() {
      return receiver_bank_account;
    }


    /**
      * Sets the value of the 'receiver_bank_account' field.
      * @param value The value of 'receiver_bank_account'.
      * @return This builder.
      */
    public FraudulentTransactionResult.Builder setReceiverBankAccount(java.lang.CharSequence value) {
      validate(fields()[3], value);
      this.receiver_bank_account = value;
      fieldSetFlags()[3] = true;
      return this;
    }

    /**
      * Checks whether the 'receiver_bank_account' field has been set.
      * @return True if the 'receiver_bank_account' field has been set, false otherwise.
      */
    public boolean hasReceiverBankAccount() {
      return fieldSetFlags()[3];
    }


    /**
      * Clears the value of the 'receiver_bank_account' field.
      * @return This builder.
      */
    public FraudulentTransactionResult.Builder clearReceiverBankAccount() {
      receiver_bank_account = null;
      fieldSetFlags()[3] = false;
      return this;
    }

    /**
      * Gets the value of the 'receiver_id' field.
      * @return The value.
      */
    public java.lang.CharSequence getReceiverId() {
      return receiver_id;
    }


    /**
      * Sets the value of the 'receiver_id' field.
      * @param value The value of 'receiver_id'.
      * @return This builder.
      */
    public FraudulentTransactionResult.Builder setReceiverId(java.lang.CharSequence value) {
      validate(fields()[4], value);
      this.receiver_id = value;
      fieldSetFlags()[4] = true;
      return this;
    }

    /**
      * Checks whether the 'receiver_id' field has been set.
      * @return True if the 'receiver_id' field has been set, false otherwise.
      */
    public boolean hasReceiverId() {
      return fieldSetFlags()[4];
    }


    /**
      * Clears the value of the 'receiver_id' field.
      * @return This builder.
      */
    public FraudulentTransactionResult.Builder clearReceiverId() {
      receiver_id = null;
      fieldSetFlags()[4] = false;
      return this;
    }

    /**
      * Gets the value of the 'amount' field.
      * @return The value.
      */
    public float getAmount() {
      return amount;
    }


    /**
      * Sets the value of the 'amount' field.
      * @param value The value of 'amount'.
      * @return This builder.
      */
    public FraudulentTransactionResult.Builder setAmount(float value) {
      validate(fields()[5], value);
      this.amount = value;
      fieldSetFlags()[5] = true;
      return this;
    }

    /**
      * Checks whether the 'amount' field has been set.
      * @return True if the 'amount' field has been set, false otherwise.
      */
    public boolean hasAmount() {
      return fieldSetFlags()[5];
    }


    /**
      * Clears the value of the 'amount' field.
      * @return This builder.
      */
    public FraudulentTransactionResult.Builder clearAmount() {
      fieldSetFlags()[5] = false;
      return this;
    }

    /**
      * Gets the value of the 'status' field.
      * @return The value.
      */
    public java.lang.CharSequence getStatus() {
      return status;
    }


    /**
      * Sets the value of the 'status' field.
      * @param value The value of 'status'.
      * @return This builder.
      */
    public FraudulentTransactionResult.Builder setStatus(java.lang.CharSequence value) {
      validate(fields()[6], value);
      this.status = value;
      fieldSetFlags()[6] = true;
      return this;
    }

    /**
      * Checks whether the 'status' field has been set.
      * @return True if the 'status' field has been set, false otherwise.
      */
    public boolean hasStatus() {
      return fieldSetFlags()[6];
    }


    /**
      * Clears the value of the 'status' field.
      * @return This builder.
      */
    public FraudulentTransactionResult.Builder clearStatus() {
      status = null;
      fieldSetFlags()[6] = false;
      return this;
    }

    /**
      * Gets the value of the 'evaluation' field.
      * @return The value.
      */
    public java.lang.CharSequence getEvaluation() {
      return evaluation;
    }


    /**
      * Sets the value of the 'evaluation' field.
      * @param value The value of 'evaluation'.
      * @return This builder.
      */
    public FraudulentTransactionResult.Builder setEvaluation(java.lang.CharSequence value) {
      validate(fields()[7], value);
      this.evaluation = value;
      fieldSetFlags()[7] = true;
      return this;
    }

    /**
      * Checks whether the 'evaluation' field has been set.
      * @return True if the 'evaluation' field has been set, false otherwise.
      */
    public boolean hasEvaluation() {
      return fieldSetFlags()[7];
    }


    /**
      * Clears the value of the 'evaluation' field.
      * @return This builder.
      */
    public FraudulentTransactionResult.Builder clearEvaluation() {
      evaluation = null;
      fieldSetFlags()[7] = false;
      return this;
    }

    /**
      * Gets the value of the 'transfer_date' field.
      * @return The value.
      */
    public java.lang.CharSequence getTransferDate() {
      return transfer_date;
    }


    /**
      * Sets the value of the 'transfer_date' field.
      * @param value The value of 'transfer_date'.
      * @return This builder.
      */
    public FraudulentTransactionResult.Builder setTransferDate(java.lang.CharSequence value) {
      validate(fields()[8], value);
      this.transfer_date = value;
      fieldSetFlags()[8] = true;
      return this;
    }

    /**
      * Checks whether the 'transfer_date' field has been set.
      * @return True if the 'transfer_date' field has been set, false otherwise.
      */
    public boolean hasTransferDate() {
      return fieldSetFlags()[8];
    }


    /**
      * Clears the value of the 'transfer_date' field.
      * @return This builder.
      */
    public FraudulentTransactionResult.Builder clearTransferDate() {
      transfer_date = null;
      fieldSetFlags()[8] = false;
      return this;
    }

    /**
      * Gets the value of the 'sender_bank' field.
      * @return The value.
      */
    public java.lang.CharSequence getSenderBank() {
      return sender_bank;
    }


    /**
      * Sets the value of the 'sender_bank' field.
      * @param value The value of 'sender_bank'.
      * @return This builder.
      */
    public FraudulentTransactionResult.Builder setSenderBank(java.lang.CharSequence value) {
      validate(fields()[9], value);
      this.sender_bank = value;
      fieldSetFlags()[9] = true;
      return this;
    }

    /**
      * Checks whether the 'sender_bank' field has been set.
      * @return True if the 'sender_bank' field has been set, false otherwise.
      */
    public boolean hasSenderBank() {
      return fieldSetFlags()[9];
    }


    /**
      * Clears the value of the 'sender_bank' field.
      * @return This builder.
      */
    public FraudulentTransactionResult.Builder clearSenderBank() {
      sender_bank = null;
      fieldSetFlags()[9] = false;
      return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public FraudulentTransactionResult build() {
      try {
        FraudulentTransactionResult record = new FraudulentTransactionResult();
        record.transaction_id = fieldSetFlags()[0] ? this.transaction_id : (java.lang.CharSequence) defaultValue(fields()[0]);
        record.sender_bank_account = fieldSetFlags()[1] ? this.sender_bank_account : (java.lang.CharSequence) defaultValue(fields()[1]);
        record.sender_id = fieldSetFlags()[2] ? this.sender_id : (java.lang.CharSequence) defaultValue(fields()[2]);
        record.receiver_bank_account = fieldSetFlags()[3] ? this.receiver_bank_account : (java.lang.CharSequence) defaultValue(fields()[3]);
        record.receiver_id = fieldSetFlags()[4] ? this.receiver_id : (java.lang.CharSequence) defaultValue(fields()[4]);
        record.amount = fieldSetFlags()[5] ? this.amount : (java.lang.Float) defaultValue(fields()[5]);
        record.status = fieldSetFlags()[6] ? this.status : (java.lang.CharSequence) defaultValue(fields()[6]);
        record.evaluation = fieldSetFlags()[7] ? this.evaluation : (java.lang.CharSequence) defaultValue(fields()[7]);
        record.transfer_date = fieldSetFlags()[8] ? this.transfer_date : (java.lang.CharSequence) defaultValue(fields()[8]);
        record.sender_bank = fieldSetFlags()[9] ? this.sender_bank : (java.lang.CharSequence) defaultValue(fields()[9]);
        return record;
      } catch (org.apache.avro.AvroMissingFieldException e) {
        throw e;
      } catch (java.lang.Exception e) {
        throw new org.apache.avro.AvroRuntimeException(e);
      }
    }
  }

  @SuppressWarnings("unchecked")
  private static final org.apache.avro.io.DatumWriter<FraudulentTransactionResult>
    WRITER$ = (org.apache.avro.io.DatumWriter<FraudulentTransactionResult>)MODEL$.createDatumWriter(SCHEMA$);

  @Override public void writeExternal(java.io.ObjectOutput out)
    throws java.io.IOException {
    WRITER$.write(this, SpecificData.getEncoder(out));
  }

  @SuppressWarnings("unchecked")
  private static final org.apache.avro.io.DatumReader<FraudulentTransactionResult>
    READER$ = (org.apache.avro.io.DatumReader<FraudulentTransactionResult>)MODEL$.createDatumReader(SCHEMA$);

  @Override public void readExternal(java.io.ObjectInput in)
    throws java.io.IOException {
    READER$.read(this, SpecificData.getDecoder(in));
  }

  @Override protected boolean hasCustomCoders() { return true; }

  @Override public void customEncode(org.apache.avro.io.Encoder out)
    throws java.io.IOException
  {
    out.writeString(this.transaction_id);

    out.writeString(this.sender_bank_account);

    out.writeString(this.sender_id);

    out.writeString(this.receiver_bank_account);

    out.writeString(this.receiver_id);

    out.writeFloat(this.amount);

    out.writeString(this.status);

    out.writeString(this.evaluation);

    out.writeString(this.transfer_date);

    out.writeString(this.sender_bank);

  }

  @Override public void customDecode(org.apache.avro.io.ResolvingDecoder in)
    throws java.io.IOException
  {
    org.apache.avro.Schema.Field[] fieldOrder = in.readFieldOrderIfDiff();
    if (fieldOrder == null) {
      this.transaction_id = in.readString(this.transaction_id instanceof Utf8 ? (Utf8)this.transaction_id : null);

      this.sender_bank_account = in.readString(this.sender_bank_account instanceof Utf8 ? (Utf8)this.sender_bank_account : null);

      this.sender_id = in.readString(this.sender_id instanceof Utf8 ? (Utf8)this.sender_id : null);

      this.receiver_bank_account = in.readString(this.receiver_bank_account instanceof Utf8 ? (Utf8)this.receiver_bank_account : null);

      this.receiver_id = in.readString(this.receiver_id instanceof Utf8 ? (Utf8)this.receiver_id : null);

      this.amount = in.readFloat();

      this.status = in.readString(this.status instanceof Utf8 ? (Utf8)this.status : null);

      this.evaluation = in.readString(this.evaluation instanceof Utf8 ? (Utf8)this.evaluation : null);

      this.transfer_date = in.readString(this.transfer_date instanceof Utf8 ? (Utf8)this.transfer_date : null);

      this.sender_bank = in.readString(this.sender_bank instanceof Utf8 ? (Utf8)this.sender_bank : null);

    } else {
      for (int i = 0; i < 10; i++) {
        switch (fieldOrder[i].pos()) {
        case 0:
          this.transaction_id = in.readString(this.transaction_id instanceof Utf8 ? (Utf8)this.transaction_id : null);
          break;

        case 1:
          this.sender_bank_account = in.readString(this.sender_bank_account instanceof Utf8 ? (Utf8)this.sender_bank_account : null);
          break;

        case 2:
          this.sender_id = in.readString(this.sender_id instanceof Utf8 ? (Utf8)this.sender_id : null);
          break;

        case 3:
          this.receiver_bank_account = in.readString(this.receiver_bank_account instanceof Utf8 ? (Utf8)this.receiver_bank_account : null);
          break;

        case 4:
          this.receiver_id = in.readString(this.receiver_id instanceof Utf8 ? (Utf8)this.receiver_id : null);
          break;

        case 5:
          this.amount = in.readFloat();
          break;

        case 6:
          this.status = in.readString(this.status instanceof Utf8 ? (Utf8)this.status : null);
          break;

        case 7:
          this.evaluation = in.readString(this.evaluation instanceof Utf8 ? (Utf8)this.evaluation : null);
          break;

        case 8:
          this.transfer_date = in.readString(this.transfer_date instanceof Utf8 ? (Utf8)this.transfer_date : null);
          break;

        case 9:
          this.sender_bank = in.readString(this.sender_bank instanceof Utf8 ? (Utf8)this.sender_bank : null);
          break;

        default:
          throw new java.io.IOException("Corrupt ResolvingDecoder.");
        }
      }
    }
  }
}










