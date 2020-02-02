package cn.net.hlk.data.service.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.net.hlk.data.mapper.LogMapper;
import cn.net.hlk.data.pojo.Page;
import cn.net.hlk.data.pojo.PageData;
import cn.net.hlk.data.service.LogService;

@Service
public class LogServiceImpl extends BaseServiceImple implements LogService{

	@Autowired
	LogMapper logMapper;
	
	
	@Override
	public List<PageData> queryLogPgListPage(Page page) {
		List<PageData> pdList = logMapper.queryLogPgListPage(page);
		return pdList;
	}

}
