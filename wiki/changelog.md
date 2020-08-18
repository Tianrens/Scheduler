# Change Log
All notable changes/commits to this Scheduler project will be recorded in this log


## [Unreleased]

&nbsp;
## [0.8.0] - 2020-08-13
## Added 
- Added Wiki folder and related md files
- TopologyFinder tests
- Output parsing tests
- More graph generator tests
- Content in Main to run scheduling system

&nbsp;
## [0.7.0] - 2020-08-12
## Added 
- Scheduler Tests
- GraphGenerator Tests
- CLI tests

## Changes
- Finalised output parsing implementation

## Removed
- removed the Program mode Enums

&nbsp;
## [0.6.0] - 2020-08-11
## Added
- A simple Scheduler for milestone 1
- extra error check for when CLI receives no arguments

&nbsp;
## [0.5.0] - 2020-08-10
### Added
- Output parsing functionality to DOTDataParser
- Added a variety of error checks and CLIException throws in the AppConfigBuilder

### Changed
- Merged Task class functionality to Node Class
- Adjusted error message to include help for the CLI

### Removed
- Task Class

### Fixed
- TopologyFinder's inner loop breaking

&nbsp;
## [0.4.0] - 2020-08-09
### Added
- Processor model
- Schedule model
- Task wrapper of Node
- additional getters/setters to Node
- TopologyFinder to generate a topology of nodes for a graph

### Changed
- Uncommented commented-out features in Node, Graph and GraphGenerator

&nbsp;
## [0.3.0] - 2020-08-07
### Added
- Command Line interface to handle receiving of scheduler parameters
- Node model 
- Graph model
- Implemented structure for basic scheduling

&nbsp;
## [0.2.0] - 2020-08-06
### Added
- Enums to cover each scheduler mode
- GraphGenerator to generate graphs from .dot files
- DOTParser and interface to handle .dot file parsing

&nbsp;
## [0.1.0] - 2020-08-04
### Added
- Created Gradle Project
- Added basic structure for models and services

