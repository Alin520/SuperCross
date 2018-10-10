// CoreService.aidl
package com.alin.cross.core;

import com.alin.cross.request.Request;
import com.alin.cross.response.Response;

interface CoreService {
    Response send(in Request request);
}
