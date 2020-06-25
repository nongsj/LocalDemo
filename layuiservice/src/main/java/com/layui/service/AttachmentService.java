package com.layui.service;

import com.layui.mapper.AttachmentMapper;
import com.layui.entity.Attachment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AttachmentService extends BaseService<Attachment, String, AttachmentMapper> {

	@Autowired
	private AttachmentMapper attachmentMapper;

	@Override
	protected AttachmentMapper getDao() {
		return attachmentMapper;
	}

}
