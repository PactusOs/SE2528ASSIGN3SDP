# Assignment 3 | Bridge Pattern

Name: Otegen Beymzhan  
Group: SE-2528  
Topic: A - Drawing  
Repository URL (publication target, not yet published): https://github.com/PactusOs/SE2528ASSIGN3SDP  
Base commit: `46fa51ea04893118e337dd3806e101e989689357`  
I3 source commit: `3bb9e3f3bda2ceb66970dddb91b517edd4b61b4c`  
Submitted commit: the full SHA in `submission.txt`; verify with `git rev-parse HEAD`.

## Role map

| Role | Class | Source |
|---|---|---|
| Abstraction | Shape | src/Shape.java |
| A1 | Circle | src/Circle.java |
| A2 | Square | src/Square.java |
| Implementor | Renderer | src/Renderer.java |
| I1 | VectorRenderer | src/VectorRenderer.java |
| I2 | RasterRenderer | src/RasterRenderer.java |
| I3 | AsciiRenderer | src/AsciiRenderer.java |
| Client | Main | src/Main.java |

## Review locations

| Item | Location |
|---|---|
| Interface-typed bridge field | `src/Shape.java:3` - private `Renderer implementation` |
| Constructor injection | `src/Shape.java:4` |
| Public `execute()` contract | `src/Shape.java:17` |
| Refined `execute()` methods | `src/Circle.java:10`, `src/Square.java:10` |
| Runtime replacement | `src/Shape.java:11` - `setImplementation(Renderer)` |
| Interface delegation | `src/Shape.java:14` - `render(...)` |
| T5 setup and state checks | `src/Main.java:20` - `testSwitch()` |
| Reference equality with `==` | `src/Main.java:28` |
| Actual/expected comparison and counting | `src/Main.java:4` - `check(...)` |
| Ordered T1-T7 demonstration | `src/Main.java:38` - `demo()` |

## Build and run

From the extracted project folder, using JDK 17:

```sh
javac --release 17 -encoding UTF-8 -d out "@sources.txt"
java -cp out Main --demo
```

No input, IDE, external Java libraries, network, or hardware is needed. All classes use the default package. The provided run was compiled for Java 17 with OpenJDK 21.0.11 and executed on that runtime.

To capture a new run:

```sh
java -cp out Main --demo > demo-output.txt
```

## Expected T1-T7 output

```text
T1 PASS | Circle + VectorRenderer | result=VECTOR circle radius=2
T2 PASS | Circle + RasterRenderer | result=[RASTER] circle {radius=2}
T3 PASS | Square + VectorRenderer | result=VECTOR square side=3
T4 PASS | Square + RasterRenderer | result=[RASTER] square {side=3}
T5 PASS | Circle + VectorRenderer -> RasterRenderer | sameObject=true | stateUnchanged=true
  id=C1->C1 | radius=2->2
  before=VECTOR circle radius=2 | after=[RASTER] circle {radius=2}
T6 PASS | Circle + AsciiRenderer | result=ASCII | circle | radius=2 |
T7 PASS | Square + AsciiRenderer | result=ASCII | square | side=3 |
SUMMARY: 7/7 PASS
```

Circle stores ID `C1` and radius `2`; Square stores ID `S1` and side `3`. The same data is used across each shape's renderer combinations. Renderers produce different text descriptions, preserving the shape, dimension label, and value.

For T5, `original` and `circle` reference the same Circle. The original ID and radius are saved before execution. Only the renderer reference is replaced. The check computes `original==circle`, compares the saved state to the state after replacement, and compares both actual renderings to fixed expected text. Expected booleans and values are test expectations, not printed success labels.

Every check calls application code. The common `check(...)` method compares actual and expected strings, calculates PASS/FAIL, counts all checks, and prints the expected value for a failure. A failed comparison does not stop later checks. The summary is calculated from the counters.

## Bridge design

The two dimensions are shape behavior (`Circle`, `Square`) and output format (`VectorRenderer`, `RasterRenderer`, `AsciiRenderer`). `Shape` holds a constructor-injected `Renderer`; each subclass supplies its own retained shape data through `render(...)`. Shape classes never construct, cast to, or inspect concrete renderers. There are no combination subclasses. [1]

One call: `Circle.execute()` -> `Shape.render("circle","radius",radius)` -> the selected `Renderer.render(...)` implementation.

`id`, `radius`, and `side` are private final fields. The renderer reference is private and can change only through the constructor or `setImplementation(...)`.

The matching UML is in `report.pdf` and `uml.svg`. Its editable UML class-diagram source is `uml.dot`, rendered with Graphviz. Regeneration is optional and is not part of the Java build:

```sh
neato -n2 -Tsvg uml.dot -o uml.svg
```

## Independent extension and Git evidence

The local history is included in `.git/`; keep that hidden folder when publishing the project. No GitHub repository was created or modified.

The `base` tag points to `46fa51ea04893118e337dd3806e101e989689357`. That version was compiled and ran T1-T5 with `SUMMARY: 5/5 PASS` before the base commit. It contains only I1 and I2.

The `extension` tag points to `3bb9e3f3bda2ceb66970dddb91b517edd4b61b4c`. The extension adds `src/AsciiRenderer.java`, two test calls in `src/Main.java`, and the new source path in `sources.txt`. It ran T1-T7 with `SUMMARY: 7/7 PASS` before the extension commit. No existing abstraction, interface, I1, or I2 source changed. A later documentation commit adds the report and evidence without changing Java code.

`extension.diff` was captured after committing I3 using:

```sh
git diff 46fa51ea04893118e337dd3806e101e989689357 HEAD -- src > extension.diff
```

The exact Java source change set is:

```text
A  src/AsciiRenderer.java
M  src/Main.java
```

Review the history and regenerate the source diff:

```sh
git log --oneline --reverse
git diff base HEAD -- src
```

Trade-off: two hierarchies and an extra delegated call add more types and more steps to trace than one fixed-format implementation. The benefit here is that all six combinations reuse the same two shape classes.

Bridge separates independently changing abstractions and implementations. Adapter instead translates an incompatible existing API into the interface a client expects. This program has no incompatible legacy API; all renderers already implement `Renderer`. [1][2]

## Publish and submit

Create an empty GitHub repository named `SE2528ASSIGN3SDP` under `PactusOs`, without adding an initial README. The URL above is the intended destination, not a claim that publication has already happened. Then run from this extracted folder:

```sh
git remote add origin https://github.com/PactusOs/SE2528ASSIGN3SDP.git
git push -u origin main
git push origin base extension
git rev-parse HEAD
```

Upload `Assignment3_SE2528_Otegen_Beymzhan.zip` to Moodle. Supply the published repository URL and exact final commit hash in the submission text. `submission.txt` contains the prepared local snapshot's hash. Any later edits require a new commit, a matching ZIP, and the new hash.

After edits, build, capture the output, commit the edited files, and recreate the ZIP from the new HEAD:

```sh
javac --release 17 -encoding UTF-8 -d out "@sources.txt"
java -cp out Main --demo > demo-output.txt
git add .
git commit -m "Finalize submission"
git archive --format=zip --output=../Assignment3_SE2528_Otegen_Beymzhan.zip HEAD
git rev-parse HEAD
```

## Sources and permitted use

Erich Gamma, Richard Helm, Ralph Johnson, John Vlissides. *Design Patterns: Adapter*, publisher-hosted excerpt from *Design Patterns: Elements of Reusable Object-Oriented Software*. InformIT, 23 October 2009. https://www.informit.com/articles/article.aspx?p=1398600

This package was generated with AI assistance. Section 9 permits AI only for reference examples and clarification, subject to the syllabus; it requires the student to write and understand their own code and report. This package does not override that rule. The individual defense is mandatory. [1]
