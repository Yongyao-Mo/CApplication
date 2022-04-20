package com.example.capplication;

import android.accessibilityservice.AccessibilityService;
import android.os.Message;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

public class MyAccessibilityService extends AccessibilityService {
    private final String TAG = getClass().getName();

    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
        Log.v(TAG, "onServiceConnected");
    }

    void addCharSequence(StringBuilder stringBuilder, CharSequence sequence) {
        if (sequence == null || sequence.length() == 0) {
            return;
        }
        stringBuilder.append(sequence);
        stringBuilder.append('\n');
    }

    void getNodeInfoContent(AccessibilityNodeInfo nodeInfo, StringBuilder stringBuilder) {
        if (nodeInfo == null) {
            return;
        }
        if (nodeInfo.getPackageName() != null && nodeInfo.getPackageName().equals("com.example.capplication")) {
            return;
        }
        if (nodeInfo.getText() != null && nodeInfo.getText().equals("CApplication")) {
            return;
        }
        for (int i = 0; i < nodeInfo.getChildCount(); i++) {
            getNodeInfoContent(nodeInfo.getChild(i), stringBuilder);
        }
        addCharSequence(stringBuilder, nodeInfo.getText());
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        if (event.getText() != null && event.getText().contains("CApplication")) {
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        if (event.getText() != null && event.getText().size() > 0) {
            for (CharSequence sequence : event.getText()) {
                addCharSequence(stringBuilder, sequence);
            }
        }

        switch (event.getEventType()) {
            case AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED:
            case AccessibilityEvent.TYPE_VIEW_TEXT_CHANGED:
                getNodeInfoContent(event.getSource(), stringBuilder);
                break;
            default:
                break;
            case AccessibilityEvent.TYPE_ANNOUNCEMENT:
                break;
            case AccessibilityEvent.TYPE_ASSIST_READING_CONTEXT:
                break;
            case AccessibilityEvent.TYPE_GESTURE_DETECTION_END:
                break;
            case AccessibilityEvent.TYPE_GESTURE_DETECTION_START:
                break;
            case AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED:
                break;
            case AccessibilityEvent.TYPE_TOUCH_EXPLORATION_GESTURE_END:
                break;
            case AccessibilityEvent.TYPE_TOUCH_EXPLORATION_GESTURE_START:
                break;
            case AccessibilityEvent.TYPE_TOUCH_INTERACTION_END:
                break;
            case AccessibilityEvent.TYPE_TOUCH_INTERACTION_START:
                break;
            case AccessibilityEvent.TYPE_VIEW_ACCESSIBILITY_FOCUSED:
                break;
            case AccessibilityEvent.TYPE_VIEW_ACCESSIBILITY_FOCUS_CLEARED:
                break;
            case AccessibilityEvent.TYPE_VIEW_CLICKED:
                break;
            case AccessibilityEvent.TYPE_VIEW_CONTEXT_CLICKED:
                break;
            case AccessibilityEvent.TYPE_VIEW_FOCUSED:
                break;
            case AccessibilityEvent.TYPE_VIEW_HOVER_ENTER:
                break;
            case AccessibilityEvent.TYPE_VIEW_HOVER_EXIT:
                break;
            case AccessibilityEvent.TYPE_VIEW_LONG_CLICKED:
                break;
            case AccessibilityEvent.TYPE_VIEW_SCROLLED:
                break;
            case AccessibilityEvent.TYPE_VIEW_SELECTED:
                break;
            case AccessibilityEvent.TYPE_VIEW_TEXT_SELECTION_CHANGED:
                break;
            case AccessibilityEvent.TYPE_VIEW_TEXT_TRAVERSED_AT_MOVEMENT_GRANULARITY:
                break;
            case AccessibilityEvent.TYPE_WINDOWS_CHANGED:
                break;
            case AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED:
                break;
        }
        Log.v(TAG, stringBuilder.toString());
        if (MainActivity.getMainActivity() != null) {
            Message msg = new Message();
            msg.what = 2;
            msg.obj = stringBuilder.toString();
            MainActivity.getMainActivity().getHandler().sendMessage(msg);
        }
    }

    @Override
    public void onInterrupt() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
