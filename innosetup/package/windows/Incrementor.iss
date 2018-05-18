;This file will be executed next to the application bundle image
;I.e. current directory will contain folder Incrementor with application files
[Setup]
AppId={{fxApplication}}
AppName=Incrementor
AppVersion=1.0
AppVerName=Incrementor 1.0
AppPublisher=
AppComments=EcoConfigurator
AppCopyright=Copyright (C) 2018
;AppPublisherURL=http://java.com/
;AppSupportURL=http://java.com/
;AppUpdatesURL=http://java.com/
DefaultDirName={pf}\NaxosGaming\Incrementor
DisableStartupPrompt=Yes
DisableDirPage=No
DisableProgramGroupPage=Yes
DisableReadyPage=Yes
DisableFinishedPage=Yes
DisableWelcomePage=Yes
DefaultGroupName=
;Optional License
LicenseFile=
;WinXP or above
MinVersion=0,5.1 
OutputBaseFilename=Incrementor-1.0
Compression=lzma
SolidCompression=yes
PrivilegesRequired=lowest
SetupIconFile=Incrementor\Incrementor.ico
UninstallDisplayIcon={app}\Incrementor.ico
UninstallDisplayName=Incrementor
WizardImageStretch=No
WizardSmallImageFile=Incrementor-setup-icon.bmp   
ArchitecturesInstallIn64BitMode=x64


[Languages]
Name: "english"; MessagesFile: "compiler:Default.isl"

[Files]
Source: "Incrementor\Incrementor.exe"; DestDir: "{app}"; Flags: ignoreversion
Source: "Incrementor\*"; DestDir: "{app}"; Flags: ignoreversion recursesubdirs createallsubdirs

[Icons]
Name: "{group}\Incrementor"; Filename: "{app}\Incrementor.exe"; IconFilename: "{app}\Incrementor.ico"; Check: returnTrue()
Name: "{commondesktop}\Incrementor"; Filename: "{app}\Incrementor.exe";  IconFilename: "{app}\Incrementor.ico"; Check: returnFalse()


[Run]
Filename: "{app}\Incrementor.exe"; Parameters: "-Xappcds:generatecache"; Check: returnFalse()
Filename: "{app}\Incrementor.exe"; Description: "{cm:LaunchProgram,Incrementor}"; Flags: nowait postinstall skipifsilent; Check: returnTrue()
Filename: "{app}\Incrementor.exe"; Parameters: "-install -svcName ""Incrementor"" -svcDesc ""Incrementor"" -mainExe ""Incrementor.exe""  "; Check: returnFalse()

[UninstallRun]
Filename: "{app}\Incrementor.exe "; Parameters: "-uninstall -svcName Incrementor -stopOnUninstall"; Check: returnFalse()

[Code]
function returnTrue(): Boolean;
begin
  Result := True;
end;

function returnFalse(): Boolean;
begin
  Result := False;
end;

function InitializeSetup(): Boolean;
begin
// Possible future improvements:
//   if version less or same => just launch app
//   if upgrade => check if same app is running and wait for it to exit
//   Add pack200/unpack200 support? 
  Result := True;
end;  
