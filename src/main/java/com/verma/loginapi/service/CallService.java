package com.verma.loginapi.service;

import com.verma.loginapi.dto.CallRequest;
import com.verma.loginapi.dto.CallResponse;
import com.verma.loginapi.model.Call;
import com.verma.loginapi.repository.CallRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CallService {

    @Autowired
    private CallRepository callRepository;

    public CallResponse processCall(CallRequest request) {
        // Persist only when call is accepted or ended
        if ("accept".equalsIgnoreCase(request.getAction())) {
            Call call = new Call();
            call.setCallerPhone(request.getCallerPhone());
            call.setReceiverPhone(request.getReceiverPhone());
            call.setType(request.getType());
            call.setStatus("accepted");
            callRepository.save(call);
        }

        if ("end".equalsIgnoreCase(request.getAction())) {
            Call call = new Call();
            call.setCallerPhone(request.getCallerPhone());
            call.setReceiverPhone(request.getReceiverPhone());
            call.setType(request.getType());
            call.setStatus("ended");
            callRepository.save(call);
        }

        return new CallResponse(
                "success",
                "Call action processed successfully",
                request.getCallerPhone(),
                request.getReceiverPhone(),
                request.getAction()
        );
    }
}
