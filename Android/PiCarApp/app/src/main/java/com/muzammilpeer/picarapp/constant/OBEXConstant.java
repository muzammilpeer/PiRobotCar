package com.muzammilpeer.picarapp.constant;

/**
 * Created by muzammilpeer on 29/11/2017.
 */

public class OBEXConstant {
    public static final byte FINAL_BIT = (byte) 0x80;

    public static final byte CONNECT = 0x00 | FINAL_BIT; //*high bit always set Connect choose your partner, negotiate capabilities
    public static final byte DISCONNECT = 0x01 | FINAL_BIT; //*high bit always set Disconnect signal the end of the session
    public static final byte PUT = 0x02; //(0x82) Put send an object
    public static final byte PUT_FINAL = PUT | FINAL_BIT;
    public static final byte GET = 0x03; //(0x83) Get get an object
    public static final byte GET_FINAL = GET | FINAL_BIT; //(0x83) Get get an object
    public static final byte SETPATH = 0x05;
    public static final byte SETPATH_FINAL = SETPATH | FINAL_BIT;
    public static final byte SESSION = 0x07;
    public static final byte ABORT = (byte) 0xFF;

    public static final byte OBEX_RESPONSE_CONTINUE = (byte) 0x90;
    public static final byte OBEX_RESPONSE_SUCCESS = (byte) 0xA0;
}
