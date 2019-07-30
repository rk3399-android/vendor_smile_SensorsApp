LOCAL_PATH := $(call my-dir)

########################
include $(CLEAR_VARS)

LOCAL_PACKAGE_NAME := SensorsApp
LOCAL_MODULE_TAGS := optional

LOCAL_RESOURCE_DIR := vendor/smile/SensorsApp/app/src/main/res
LOCAL_SRC_FILES := $(call all-java-files-under, java)
LOCAL_PRIVILEGED_MODULE := false
LOCAL_CERTIFICATE:= platform

LOCAL_STATIC_JAVA_LIBRARIES += android-support-v4
LOCAL_STATIC_JAVA_LIBRARIES += messageformat
LOCAL_STATIC_JAVA_LIBRARIES += android-support-v13
LOCAL_STATIC_JAVA_LIBRARIES += android-support-v7-appcompat
LOCAL_STATIC_JAVA_LIBRARIES += android-support-v7-recyclerview

LOCAL_RESOURCE_DIR += frameworks/support/v7/appcompat/res

LOCAL_AAPT_FLAGS := --auto-add-overlay
LOCAL_AAPT_FLAGS += --extra-packages android.support.v7.appcompat
LOCAL_AAPT_FLAGS += --extra-packages android.support.v7.recyclerview
LOCAL_AAPT_FLAGS += --extra-packages com.android.messageformat

LOCAL_PROGUARD_ENABLED := disabled

include packages/apps/Car/libs/car-stream-ui-lib/car-stream-ui-lib.mk
include packages/services/Car/car-support-lib/car-support.mk

include $(BUILD_PACKAGE)

include $(call all-makefiles-under,$(LOCAL_PATH))
