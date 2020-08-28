# Change Log
All notable changes/commits to this Scheduler project will be recorded in this log


## [Unreleased]

&nbsp;
## [1.7.0] - 2020-08-28
### Removed
- Out-dated classes e.g. SimpleProcessorScheduler

&nbsp;
## [1.6.0] - 2020-08-27
### Changed
- Changed jar name to scheduler.jar
- No longer use Processor Sets for pruning
- Added pruning checks to Schedule Comparators

### Removed
- Processor Class

&nbsp;
## [1.5.0] - 2020-08-26
### Added
- Gantt Chart to visualisation
- Visualisation test
- Algorithm visualisation
- New ScheduleQueue to handle pruning

### Changed
- Refactored ELSComparatorChanges
- ScheduleComparator changed to also handle earliest start time

&nbsp;
## [1.4.0] - 2020-08-25
### Added
- New Heuristics (Idle time, Bottom level, DRT)
- Algorithm Integration tests
- Add new Processor Set into Schedules for duplication detection

### Changed
- Replace old heuristics with new
- Shifted Bottom level calculations to Node Class

&nbsp;
## [1.3.0] - 2020-08-22
### Added
- Parallelisation/Multithreading to the A* algorithm
- A valid schedule checker
- JavaFX/Visualisation skeleton

### Changed
- Heuristics now account for parent communication/remote costs

&nbsp;
## [1.3.0] - 2020-08-20
### Added
- Simple & Greedy Heuristic for path estimation
- ESLModelStateExpander to expand/generate partial schedules
- A* algorithm to find the optimal schedules


&nbsp;
## [1.2.0] - 2020-08-19
### Changed
- Processor class no longer used
- Schedule class adjusted to use basic fields rather than stored objects

&nbsp;
## [1.1.0] - 2020-08-18
### Added 
- Template skeleton for further development

### Changed
- TaskNode class renamed to Node

&nbsp;
## [1.0.0] - 2020-08-18 MILESTONE 1 RELEASE
### Added 
- Added SimpleProcessScheduler which can schedule tasks onto multiple processors
- Wiki additions

### Changed
- SimpleProcessScheduler is now the primary scheduler

&nbsp;
## [0.9.0] - 2020-08-15
### Added 
- Added user instructions to ReadMe
- Status print outs on console/terminal

### Changed
- Shifted output parsing to new DOTFileWriter class

&nbsp;
## [0.8.0] - 2020-08-13
### Added 
- Added Wiki folder and related md files
- TopologyFinder tests
- Output parsing tests
- More graph generator tests
- Content in Main to run scheduling system

&nbsp;
## [0.7.0] - 2020-08-12
### Added 
- Scheduler Tests
- GraphGenerator Tests
- CLI tests

### Changes
- Finalised output parsing implementation

### Removed
- removed the Program mode Enums

&nbsp;
## [0.6.0] - 2020-08-11
### Added
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

