package com.example.gsonobj;

public class SendMessageObj {
	private String messaging_type;
	private Recipient recipient;
	private Message message;

	public String getMessaging_type() {
		return messaging_type;
	}

	public void setMessaging_type(String messaging_type) {
		this.messaging_type = messaging_type;
	}

	public Recipient getRecipient() {
		return recipient;
	}

	public void setRecipient(Recipient recipient) {
		this.recipient = recipient;
	}

	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}

}
