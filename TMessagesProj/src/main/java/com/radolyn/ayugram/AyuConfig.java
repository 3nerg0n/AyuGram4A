package com.radolyn.ayugram;

import android.app.Activity;
import android.content.SharedPreferences;

import org.telegram.messenger.ApplicationLoader;

public class AyuConfig {
    private static final Object sync = new Object();

    public static SharedPreferences preferences;
    public static SharedPreferences.Editor editor;

    public static boolean sendReadPackets;
    public static boolean sendOnlinePackets;
    public static boolean sendOfflinePacketAfterOnline;
    public static boolean sendUploadProgress;
    public static boolean useScheduledMessages;
    public static boolean keepDeletedMessages;
    public static boolean keepMessagesHistory;
    public static boolean markReadAfterSend;
    public static boolean showFromChannel;
    public static boolean keepAliveService;
    public static boolean enableAds;
    public static boolean showGhostToggleInDrawer;

    private static boolean configLoaded;

    static {
        loadConfig();
    }

    public static void loadConfig() {
        synchronized (sync) {
            if (configLoaded) {
                return;
            }

            preferences = ApplicationLoader.applicationContext.getSharedPreferences("ayuconfig", Activity.MODE_PRIVATE);
            editor = preferences.edit();

            // ~ Ghost essentials
            sendReadPackets = preferences.getBoolean("sendReadPackets", true);
            sendOnlinePackets = preferences.getBoolean("sendOnlinePackets", true);
            sendUploadProgress = preferences.getBoolean("sendUploadProgress", true);

            sendOfflinePacketAfterOnline = preferences.getBoolean("sendOfflinePacketAfterOnline", false);
            markReadAfterSend = preferences.getBoolean("markReadAfterSend", false);

            useScheduledMessages = preferences.getBoolean("useScheduledMessages", false);

            // ~ Message edits & deletion history
            keepDeletedMessages = preferences.getBoolean("keepDeletedMessages", true);
            keepMessagesHistory = preferences.getBoolean("keepMessagesHistory", true);

            // ~ QoL
            showFromChannel = preferences.getBoolean("showFromChannel", true);
            keepAliveService = preferences.getBoolean("keepAliveService", true);
            enableAds = preferences.getBoolean("enableAds", false);

            // ~ Customization
            showGhostToggleInDrawer = preferences.getBoolean("showGhostToggleInDrawer", true);

            configLoaded = true;
        }
    }

    public static boolean isGhostModeActive() {
        return !sendReadPackets && !sendOnlinePackets;
    }

    public static void setGhostMode(boolean enabled) {
        sendReadPackets = !enabled;
        sendOnlinePackets = !enabled;
        sendUploadProgress = !enabled;
        sendOfflinePacketAfterOnline = enabled;

        AyuConfig.editor.putBoolean("sendReadPackets", AyuConfig.sendReadPackets).apply();
        AyuConfig.editor.putBoolean("sendOnlinePackets", AyuConfig.sendOnlinePackets).apply();
        AyuConfig.editor.putBoolean("sendUploadProgress", AyuConfig.sendUploadProgress).apply();
        AyuConfig.editor.putBoolean("sendOfflinePacketAfterOnline", AyuConfig.sendOfflinePacketAfterOnline).apply();
    }

    public static void toggleGhostMode() {
        // giga move
        setGhostMode(!isGhostModeActive());
    }

    public static String getDeletedMark() {
        return AyuConfig.preferences.getString("deletedMarkText", "🧹");
    }
}
