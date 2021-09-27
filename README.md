# secucheck

SecuCheck is a configurable taint analysis that can run on top of the [Boomerang](https://github.com/CodeShield-Security/SPDS) (implementation of SPDS) or the [FlowDroid](https://github.com/secure-software-engineering/FlowDroid) (implementation of IFDS) data-flow solvers runnin on top of the [Soot framework](https://github.com/soot-oss/soot). 

This repository contains an IDE tool and a command-line tool of SecuCheck.


# secucheck as an IDE plugin
The plugin support for wide-range of IDEs is implemented with [MagpieBridge](https://github.com/MagpieBridge/MagpieBridge). Check our wiki for further documentation.   

# secucheck as a command-line tool
SecuCheck can be executed as a command line tool. 

# secucheck-core analysis
The core analysis is in [this repository](https://github.com/secure-software-engineering/secucheck-core). 
