run_all_in_parallel:
	make clean_it set_end_point

clean_it:
	mvn clean

set_end_point:
	make -j browserstack

############################# Android-Device #############################
android_device:
	make android_device_parallel

android_device_single:
	make ad_galaxyS10e

android_device_parallel:
	make -j ad_galaxyS10e ad_galaxyS9

ad_galaxyS10e:
	mvn test -Dend_point=android -Dand_device_name=GalaxyS10e -Dand_udid=RF8M1301XYZ -Dand_platform=Android -Dand_platform_version=9.0 -Dand_automation_name=UiAutomator2 -Dand_app=Wikipedia.apk -Denv=ad_galaxyS10e

ad_galaxyS9:
	mvn test -Dend_point=android -Dand_device_name=GalaxyS9 -Dand_udid=1c60f2d638057aaa -Dand_platform=Android -Dand_platform_version=9.0 -Dand_automation_name=UiAutomator2 -Dand_app=Wikipedia.apk -Denv=ad_galaxyS9

############################# Android-Simulator #############################
android_simulator:
	make android_simulator_parallel

android_simulator_single:
	make asim_pixel3XL

android_simulator_parallel:
	make -j asim_pixel3XL asim_nexus6P

asim_pixel3XL:
	mvn test -Dend_point=android_simulator -Dasim_device_name=Android_Emulator -Dasim_udid=emulator-5554 -Dasim_platform_name=Android -Dasim_platform_version=9 -Dasim_automation_name=UiAutomator2 -Dasim_app=Wikipedia.apk -Denv=asim_Pixel3XL

asim_nexus6P:
	mvn test -Dend_point=android_simulator -Dasim_device_name=Android_Emulator -Dasim_udid=emulator-5556 -Dasim_platform_name=Android -Dasim_platform_version=9 -Dasim_automation_name=UiAutomator2 -Dasim_app=Wikipedia.apk -Denv=asim_Nexux6P

############################# iOS-Simulator #############################
ios_simulator:
	make ios_simulator_parallel

ios_simulator_single:
	make isim_iPhoneXs_12.4

ios_simulator_parallel:
	make -j isim_iPhoneXs_12.4 isim_iPhoneXr_12.4

isim_iPhoneXs_12.4:
	mvn test -Dend_point=ios_simulator -Disim_device_name=iPhone_simulator -Disim_udid=106318D0-F3B3-479A-AC0D-483D2EF1F5CD -Disim_platform_name=iOS -Disim_platform_version=12.4 -Disim_automation_name=XCUITest -Disim_app=Wikipedia.zip -Denv=isim_iPhoneXs-12.4

isim_iPhoneXr_12.4:
	mvn test -Dend_point=ios_simulator -Disim_device_name=iPhone_simulator -Disim_udid=9F56AEB0-7259-48ED-945B-55FB918E967C -Disim_platform_name=iOS -Disim_platform_version=12.4 -Disim_automation_name=XCUITest -Disim_app=Wikipedia.zip -Denv=isim_iPhoneXr-12.4

############################# BrowserStack #############################
browserstack:
	make browserstack_parallel

browserstack_single:
	make bs_iPhoneXS

browserstack_parallel:
	make -j bs_iPhoneXS bs_iPhoneX

bs_iPhoneXS:
	mvn test -Dend_point=browserstack -Dbs_local_testing=false -Dbs_device=iPhoneXS -Dbs_app=MyApp -Denv=bs_iPhoneXS

bs_iPhoneX:
	mvn test -Dend_point=browserstack -Dbs_local_testing=false -Dbs_device=iPhoneX -Dbs_app=MyApp -Denv=bs_iPhoneX

