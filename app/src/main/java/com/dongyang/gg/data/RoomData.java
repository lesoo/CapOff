package com.dongyang.gg.data;

public class RoomData {

    private final String sender;
    private final String receiver;

    private final String roomCode;

    public RoomData(String sender, String receiver) {
        this.sender = sender;
        this.receiver = receiver;
        this.roomCode = getRoomCode(sender, receiver);
    }

    public String getSender(){ return this.sender; }
    public String getReceiver() { return this.receiver; }
    public String getRoomCode() { return this.roomCode; }


    public static String getRoomCode(String sender, String receiver){
        if(sender.compareTo(receiver) < 0){
            return sender + "-" + receiver;
        } else {
            return receiver + "-" + sender;
        }
    }

}
