#include <jni.h>
#include <string>

extern "C" JNIEXPORT jstring
Java_com_interview_willyweathertest_MainActivity_stringFromJNI(
        JNIEnv* env,
        jobject /* this */) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}
extern "C"
JNIEXPORT jstring
Java_com_interview_willyweathertest_di_module_AppModule_apiBaseUrl(JNIEnv *env, jobject thiz) {
    std::string baseUrl = "https://api.themoviedb.org/3/";
    return env->NewStringUTF(baseUrl.c_str());
}
extern "C"
JNIEXPORT jstring JNICALL
Java_com_interview_willyweathertest_common_Constants_apiKey(JNIEnv *env, jobject thiz) {
    std::string apiKey = "12c46aa19f287edbb74f31f510230176";
    return env->NewStringUTF(apiKey.c_str());
}