package com.layui.controlle;

import com.layui.entity.Attachment;
import com.layui.service.AttachmentService;
import com.layui.service.SuperService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/attachment")
public class Attachmentcontrollers extends BsaeController<Attachment, String> {

@Autowired
private AttachmentService attachmentService;

@Override
protected SuperService getService() {
return attachmentService;
}

}