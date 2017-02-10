
echo off
set NOVA_USER_NAME=
set NOVA_PASSWORD=
set NOVA_SERVER=nova.cs.tau.ac.il
set WINSCP_COM_PATH="C:\Program Files (x86)\WinSCP\WinSCP.com"
set LOCAL_BASE_DIR=WINDOWS_COMMAND_LINE\FOLDER_10_tester\testerUtils
set LOCAL_PSEUDO_MIPS_PATH=%LOCAL_BASE_DIR%\tester_pseudoMips.pmips
set NOVA_SIMULATOR_DIR=./COMPILATION/Simulator
set NOVA_PSEUDO_MIPS_PATH=%NOVA_SIMULATOR_DIR%/tester_pseudoMips.pmips
set NOVA_C_PATH=%NOVA_SIMULATOR_DIR%/tester_C.c
set LOCAL_C_PROJECT_NAME=CompiSln
set LOCAL_C_PATH=%LOCAL_BASE_DIR%\%LOCAL_C_PROJECT_NAME%\%LOCAL_C_PROJECT_NAME%\main.c
set PLINK_PATH=%LOCAL_BASE_DIR%\plink.exe
set NOVA_SIMULATOR_PATH=%NOVA_SIMULATOR_DIR%/simulator
set VS_DEV_ENV_EXE_PATH="C:\Program Files (x86)\Microsoft Visual Studio 14.0\Common7\IDE\devenv.exe"
set SLN_PATH=%LOCAL_BASE_DIR%\%LOCAL_C_PROJECT_NAME%\%LOCAL_C_PROJECT_NAME%.sln
set EXE_PATH=%LOCAL_BASE_DIR%\%LOCAL_C_PROJECT_NAME%\Debug\%LOCAL_C_PROJECT_NAME%.exe
set EXE_OUTPUT_PATH=%LOCAL_BASE_DIR%\tester_exe_output.txt

:: Uploading the pseudo mips file to nova
%WINSCP_COM_PATH% /command "open sftp://%NOVA_USER_NAME%:%NOVA_PASSWORD%@%NOVA_SERVER%/" "put %LOCAL_PSEUDO_MIPS_PATH% %NOVA_PSEUDO_MIPS_PATH%" "exit"

:: Running the simulator in nova
%PLINK_PATH% -ssh %NOVA_USER_NAME%@nova.cs.tau.ac.il -pw %NOVA_PASSWORD% "%NOVA_SIMULATOR_PATH% %NOVA_PSEUDO_MIPS_PATH% %NOVA_C_PATH%"

:: Downloading the C file from nova
%WINSCP_COM_PATH% /command "open sftp://%NOVA_USER_NAME%:%NOVA_PASSWORD%@%NOVA_SERVER%/" "get %NOVA_C_PATH% %LOCAL_C_PATH%" "exit"

:: Compiling the C file with Visual Studio
del %EXE_PATH%
%VS_DEV_ENV_EXE_PATH% %SLN_PATH% /rebuild

:: Running the EXE
%EXE_PATH% > %EXE_OUTPUT_PATH%