package cn.mapway.openapi.viewer.client.main;

import cn.mapway.openapi.viewer.client.component.Link;
import cn.mapway.openapi.viewer.client.specification.Contact;
import cn.mapway.openapi.viewer.client.specification.Info;
import cn.mapway.openapi.viewer.client.specification.License;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;

/**
 * 联系人组件
 */
public class ContactView extends Composite {
    private static ContactViewUiBinder ourUiBinder = GWT.create(ContactViewUiBinder.class);
    @UiField
    Label email;
    @UiField
    Link link;
    @UiField
    Link license;
    @UiField
    Link term;

    public ContactView() {
        initWidget(ourUiBinder.createAndBindUi(this));
    }

    public void parse(Info info) {

        Contact contact = info.contact;
        if (contact == null) {
            GWT.log("data do not contain contact information");
        } else {
            email.setText(contact.email);
            link.setText(contact.name);
            link.setHref(contact.url);
        }

        License license1 = info.license;
        if (license1 == null) {
            GWT.log("no license information");
        } else {
            license.setHref(license1.url);
            license.setText(license1.name);
        }

        if (info.termsOfService != null && info.termsOfService.length() > 0) {
            term.setText("使用说明");
            term.setHref(info.termsOfService);
            term.setTarget("_blank");
        }
    }

    interface ContactViewUiBinder extends UiBinder<HorizontalPanel, ContactView> {
    }
}