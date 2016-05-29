//
// Created by amel on 5/28/16.
//

#include "WriteFile.h"

JNIEXPORT jstring JNICALL Java_amel_hog_MainActivity_readFile(JNIEnv * env, jclass obj){
  return (*env).NewStringUTF("Hello");
}