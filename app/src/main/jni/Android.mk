# Copyright 2013 The Android Open Source Project
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#      http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.

LOCAL_PATH:= $(call my-dir)
######################################################
# bin: screenrecord
######################################################

include $(CLEAR_VARS)
LOCAL_SRC_FILES := \
	screenrecord.cpp

LOCAL_C_INCLUDES := \
	kikkat/include \
	kikkat/include/openmax

LOCAL_LDLIBS := -L$(LOCAL_PATH)/kikkat/libs -llog -lutils -lcutils -lstagefright_foundation -lstagefright -lgui -lbinder
LOCAL_CFLAGS += -Wno-multichar
LOCAL_MODULE_TAGS := optional
LOCAL_MODULE:= screenrecord_limit30

include $(BUILD_EXECUTABLE)
