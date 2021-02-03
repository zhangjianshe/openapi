package cn.mapway.openapi.viewer.client.main.test;

import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.DialogBox;

/**
 * TestDialogBox
 * 测试对话框
 *
 * @author zhangjianshe@gmail.com
 */
public class TestDialogBox extends DialogBox {

    private final CloseHandler<Boolean> closeHandler = new CloseHandler<Boolean>() {
        @Override
        public void onClose(CloseEvent<Boolean> event) {
            TestDialogBox.this.hide();
        }
    };
    TestFrame testFrame;

    public TestDialogBox() {
        setText("接口测试");
        // Enable animation.
        setAnimationEnabled(false);
        // Enable glass background.
        setGlassEnabled(true);
        testFrame = new TestFrame();
        testFrame.addCloseHandler(closeHandler);

        setWidget(testFrame);
    }

    public TestFrame getTestFrame() {
        return testFrame;
    }
}
