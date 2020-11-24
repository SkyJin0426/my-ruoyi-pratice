package com.ruoyi.web.controller.tool;

import com.ruoyi.system.util.GZIPUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.codec.binary.Base64;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;

/**
 * >
 *
 * @author: shi kai jing
 * @Date: 2020/08/24 15:12
 **/
@Api(value = "解压解密Controller", tags = {"解压解密接口"})
@RestController
@RequestMapping("/decode")
public class DecodeController {

    @ApiOperation("获取加密后的日志参数解密")
    @PostMapping("/log")
    @ResponseBody
    public Object log(@RequestBody String outputStr) throws Exception {

        //解压缩示例代码
        ByteArrayOutputStream baos2 = new ByteArrayOutputStream();
        //先用base64解码
        byte[] strZipByte = Base64.decodeBase64(outputStr);
        Boolean strReturn = GZIPUtil.decompress(strZipByte, baos2);
        if (false == strReturn) {
            return "解压缩失败";
        }
        byte[] data = baos2.toByteArray();
        baos2.flush();
        baos2.close();

        String strMessage = new String(data);
        return strMessage;
    }

    @ApiOperation("BASE64解密")
    @PostMapping("/decodeBase")
    @ResponseBody
    public Object decodeBase(@RequestBody String outputStr) {
        return new String(Base64.decodeBase64(outputStr));
    }
    @ApiOperation("BASE64解密GBK")
    @PostMapping("/decodeBaseGBK")
    @ResponseBody
    public Object decodeBaseGBK(@RequestBody String outputStr) throws Exception {
        return new String(Base64.decodeBase64(outputStr),"GBK");
    }
}
