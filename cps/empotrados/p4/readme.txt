# README.TXT

This project is a generic base for a C and Assembly project for the MC68HC908GP32.

Folders used:
- Sources: contains your sources.
- Debugger Cmd Files: Here you can place your own specific debugger command
  files.
- Startup Code: Contains the startup code of the application. You may adapt it
  to your own needs, but in such a case make sure that you create a local copy.
- Prm: Linker parameter file used for linking. Note that the file used for the linker
  is specified in the Linker Preference Panel itself (<ALT-F7> or Edit->{Current Build Target Name} settings,
  while the project window is opened).
- Libs: contains the library to be linked with.
- Debugger Project File: This *.ini file is passed to the debugger/simulator
  as configuration file. It contains various target interface settings, and path
  information as well. This file can be edited from the simulator/debugger e.g. using
  File->Configuration in the simulator/debugger.

Available CodeWarrior Targets:

- P&E PEDebug FCS-ICS-ICD
  This target is set up to generate an application to be debugged 
  with the P&E PEDebug target interface in FCS (Full Chip Simulation),
  ICS (In-Circuit Simulation) or ICD (In-Circuit Debug/Programming). 

- MMDS-MMEVS
  This target is set up to generate an application to be debugged 
  with the Motosil target interface on MMDS/MMEVS. 

- Cyclone
  This target is set up to generate an application to be debugged 
  with the Cyclone target interface. 

- MON08 (RAM)
  This target is set up to generate an application to be loaded and debugged in RAM. 
  No vectors are defined.The Preload and Postload command files are disabled by default as
  they contain debugger commands in order to load the application in flash.
 
- MON08 (FLASH) 
  This target is set up to generate an application to be loaded in Flash.
  The application is automatically loaded in FLASH EEPROM using 
  the Preload and Postload command files. The execution of the flash commands 
  in those Command Files can take some time.

Note that this project is set up for a P&E PEDebug FCS-ICS-ICD by default.
But you can easily change this to work with a target:
- launch the simulator/debugger using Project->Debug (while
  the current project window is opened)
- Choose in the simulator/debugger Component->Set Target and then choose a
  target interface
- Choose in the simulator/debugger File->Save Project so the new target
  settings are saved
  
This project includes a burner command file (.bbl) in order to generate a 
SRecord file (.s19). Remove this file from prm folder if no SRecord is needed.



Metrowerks