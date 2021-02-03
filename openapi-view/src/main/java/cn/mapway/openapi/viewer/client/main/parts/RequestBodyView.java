package cn.mapway.openapi.viewer.client.main.parts;

import cn.mapway.openapi.viewer.client.main.util.GenTableInfo;
import cn.mapway.openapi.viewer.client.main.util.TableUtil;
import cn.mapway.openapi.viewer.client.specification.MediaType;
import cn.mapway.openapi.viewer.client.specification.RequestBody;
import cn.mapway.openapi.viewer.client.specification.Schema;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.*;

import java.util.List;
import java.util.Map;

/**
 * RequestBodyView
 *
 * @author zhangjianshe@gmail.com
 */
public class RequestBodyView extends Composite {
    public void parse(RequestBody requestBody, Map<String, GenTableInfo> allSchema) {
        if (requestBody != null) {
            MediaType mediaType = requestBody.jsonType();
            Schema schema = mediaType.getSchema().resolve();
            root.clear();
            root.add(TableUtil.rootSchemaToFlexTable(schema,allSchema));
        }
    }

    interface RequestBodyViewUiBinder extends UiBinder<HTMLPanel, RequestBodyView> {
    }

    private static RequestBodyViewUiBinder ourUiBinder = GWT.create(RequestBodyViewUiBinder.class);
    @UiField
    HTMLPanel root;



    public RequestBodyView() {
        initWidget(ourUiBinder.createAndBindUi(this));
    }
}