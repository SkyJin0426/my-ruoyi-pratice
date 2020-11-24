package com.ruoyi.system.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import com.ruoyi.system.util.GZIPUtil;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.common.core.text.Convert;
import com.ruoyi.system.domain.SysOperLog;
import com.ruoyi.system.mapper.SysOperLogMapper;
import com.ruoyi.system.service.ISysOperLogService;

/**
 * 操作日志 服务层处理
 * 
 * @author ruoyi
 */
@Service
public class SysOperLogServiceImpl implements ISysOperLogService
{
    @Autowired
    private SysOperLogMapper operLogMapper;

    /**
     * 查询系统操作日志集合
     *
     * @param operLog 操作日志对象
     * @return 操作日志集合
     */
    @Override
    public List<SysOperLog> selectOperLogList(SysOperLog operLog)
    {
        return operLogMapper.selectOperLogList(operLog);
    }

    /**
     * 新增操作日志
     *
     * @param operLog 操作日志对象
     */
    @Override
    public void insertOperlog(SysOperLog operLog)
    {
        String operParam=  operLog.getOperParam();
        if(null!=operParam && operParam.length()>3500){
            try {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                Boolean strZipReturn = GZIPUtil.compress(operParam.getBytes(), baos);
                if (true == strZipReturn) {
                    byte[] output = baos.toByteArray();
                    baos.flush();
                    baos.close();
                    //压缩后的内容用base64编码
                    String outputStr = Base64.encodeBase64String(output);
                    operLog.setOperParam(outputStr);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        operLogMapper.insertOperlog(operLog);
    }

    /**
     * 批量删除系统操作日志
     * 
     * @param ids 需要删除的数据
     * @return
     */
    @Override
    public int deleteOperLogByIds(String ids)
    {
        return operLogMapper.deleteOperLogByIds(Convert.toStrArray(ids));
    }

    /**
     * 查询操作日志详细
     * 
     * @param operId 操作ID
     * @return 操作日志对象
     */
    @Override
    public SysOperLog selectOperLogById(Long operId)
    {
        return operLogMapper.selectOperLogById(operId);
    }

    /**
     * 清空操作日志
     */
    @Override
    public void cleanOperLog()
    {
        operLogMapper.cleanOperLog();
    }
}
