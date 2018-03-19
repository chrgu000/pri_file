namespace java com.hxyc.thrift

//enum RequestType {
//    SAY_HELLO,   //问好
//    QUERY_TIME,  //询问时间
//}

//struct Request {
//    1: required RequestType type;  // 请求的类型，必选
//    2: required string name;       // 发起请求的人的名字，必选
//    3: optional i32 age;           // 发起请求的人的年龄，可选
//}

exception ApiException {
    1: required i32 code;
    2: optional string reason;
}

// 服务名
service Test3 {
    string doAction(1: i32 i,2:string str) throws (1:ApiException ae); // 可能抛出异常。
}