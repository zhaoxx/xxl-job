package com.xxl.job.core.handler.impl;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;

import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.log.XxlJobLogger;
import com.xxl.job.core.rpc.codec.RpcResponse;
import com.xxl.job.core.util.HttpClientUtil;

/**
 * glue job handler
 * @author xuxueli 2016-5-19 21:05:45
 */
public class HttpJobHandler extends IJobHandler {

    private long glueUpdatetime;
    private String url;

    public HttpJobHandler(long glueUpdatetime, String url){
        this.glueUpdatetime = glueUpdatetime;
        this.url = url;
    }

    public long getGlueUpdatetime() {
        return glueUpdatetime;
    }

    @Override
    public ReturnT<String> execute(String... params) throws Exception {
        // cmd + script-file-name
//        String cmd = "bash";
//        String scriptFileName = null;
//        if (GlueTypeEnum.GLUE_SHELL == glueType) {
//            cmd = "bash";
//            scriptFileName = XxlJobExecutor.logPath.concat("gluesource/").concat(String.valueOf(jobId)).concat("_").concat(String.valueOf(glueUpdatetime)).concat(".sh");
//        } else if (GlueTypeEnum.GLUE_PYTHON == glueType) {
//            cmd = "python";
//            scriptFileName = XxlJobExecutor.logPath.concat("gluesource/").concat(String.valueOf(jobId)).concat("_").concat(String.valueOf(glueUpdatetime)).concat(".py");
//        }
        

        // make script file
//        ScriptUtil.markScriptFile(scriptFileName, gluesource);

        // log file
//        String logFileName = XxlJobExecutor.logPath.concat(XxlJobFileAppender.contextHolder.get());

        // invoke
        XxlJobLogger.log("----------- http request:"+ this.url +" -----------");
//        int exitValue = ScriptUtil.execToFile(cmd, scriptFileName, logFileName, params);
     // remote invoke
        RpcResponse rpcResponse = this.request(this.url);
        ReturnT<String> result = (!rpcResponse.isError())?ReturnT.SUCCESS:new ReturnT<String>(ReturnT.FAIL_CODE, "http response error value("+rpcResponse.getError()+") is failed");
        return result;
    }
    
    private RpcResponse request(String url) throws Exception{
    	HttpResponse response = HttpClientUtil.getRequest(url);
		if (response == null || response.getStatusLine().getStatusCode()!=200) {
			RpcResponse rpcResponse = new RpcResponse();
			rpcResponse.setError("RpcResponse byte[] is null");
			return rpcResponse;
        }
         // deserialize response
//		RpcResponse rpcResponse = (RpcResponse) HessianSerializer.deserialize(responseBytes, RpcResponse.class);
		return new RpcResponse();
    }

	
}
