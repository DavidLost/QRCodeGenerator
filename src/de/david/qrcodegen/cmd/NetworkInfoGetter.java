package de.david.qrcodegen.cmd;

public class NetworkInfoGetter {

    public static final int NETWORK_FIND_MODE = 0;
    public static final int NETWORK_KEY_FIND_MODE = 1;
    public static final int KEY_CONETNT_LINE = 33;
    public static final int LINES_BEFORE_NETWORKNAMES = 9;

    private Network[] networks;
    public boolean wlanIsSupported;

    public NetworkInfoGetter() {

        if (getNetworkNames() && getNetworkKeys()) {
            wlanIsSupported = true;
        }
        else wlanIsSupported = false;
    }

    private boolean getNetworkNames() {

        CmdCommand getNetworksCommand = new CmdCommand("netsh wlan show profile");
        getNetworksCommand.execute();
        //if (getNetworksCommand.getError().length > 0) return false;
        String[] out = getNetworksCommand.getOut();
        if (out[0].startsWith("Der Dienst")) return false;
        networks = new Network[out.length-LINES_BEFORE_NETWORKNAMES];
        System.out.println("lenght: "+networks.length);
        for (int i = 0; i < networks.length; i++) {
            networks[i] = new Network();
            networks[i].name = shortLine(out[i+LINES_BEFORE_NETWORKNAMES], NETWORK_FIND_MODE);
        }
        return true;
    }

    private boolean getNetworkKeys() {

        for (int i = 0; i < networks.length; i++) {
            CmdCommand getNetworkCommand = new CmdCommand("netsh wlan show profile \""+networks[i].name+"\" key=clear");
            //if (getNetworkCommand.getError().length > 0) return false;
            getNetworkCommand.execute();
            String[] allNetworkInfos = getNetworkCommand.getOut();
            networks[i].password = shortLine(allNetworkInfos[KEY_CONETNT_LINE], NETWORK_KEY_FIND_MODE);
        }
        return true;
    }

    public Network getNetwork(int index) {
        return networks[index];
    }

    public Network[] getNetworks() {
        return networks;
    }

    private String shortLine(String string, int mode) {
        int start = 0;
        int end = 0;
        switch (mode) {
            case 0: start = 0; end = 31;
                break;
            case 1: start = 0; end = 33;
                break;
        }
        return new StringBuilder().append(string).delete(start, end).toString();
    }

}
