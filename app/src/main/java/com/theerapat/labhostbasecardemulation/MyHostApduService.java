package com.theerapat.labhostbasecardemulation;

import android.nfc.cardemulation.HostApduService;
import android.os.Bundle;
import android.util.Log;

import java.util.Arrays;

/**
 * Created by theerapat on 11/26/2016.
 */
public class MyHostApduService extends HostApduService {
    private final String TAG = "MyHostApduService";

    private static final String CARD_ID = "F111111111";
    private static final String SELECT_APDU_HEADER = "00A40400";
    private static final byte[] SW_SUCCESS = Utils.HexStringToByteArray("9000");
    private static final byte[] SW_UNKNOWN = Utils.HexStringToByteArray("0000");
    private static final byte[] SELECT_APDU = buildSelectApdu(CARD_ID);

    public static byte[] buildSelectApdu(String aid) {
        // Format: [CLASS | INSTRUCTION | PARAMETER 1 | PARAMETER 2 | LENGTH | DATA]
        return Utils.HexStringToByteArray(SELECT_APDU_HEADER + String.format("%02X", aid.length() / 2) + aid);
    }

    @Override
    public byte[] processCommandApdu(byte[] commandApdu, Bundle bundle) {
        Log.i(TAG, "Received APDU: " + Utils.ByteArrayToHexString(commandApdu));
        if (Arrays.equals(SELECT_APDU, commandApdu)) {
            String account = AccountStorage.getAccount(this);
            byte[] accountBytes = account.getBytes();
            Log.i(TAG, "Sending account number: " + account);
            return Utils.ConcatArrays(accountBytes, SW_SUCCESS);
        }
        return SW_UNKNOWN;
    }

    @Override
    public void onDeactivated(int i) {
        if(i == DEACTIVATION_LINK_LOSS)
            Log.w(TAG, "[onDeactivated] NFC link has been deactivated or lost");
        else if (i == DEACTIVATION_DESELECTED )
            Log.w(TAG, "[onDeactivated] A different AID has been selected");
        else {
            Log.w(TAG, "[onDeactivated] Deactivated unknown");
        }
    }

}
