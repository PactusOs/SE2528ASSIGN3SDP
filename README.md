# Assignment 3 | Bridge Pattern

Name: Otegen Beymzhan  
Group: SE-2528  
Topic: Drawing  
Repository URL https://github.com/PactusOs/SE2528ASSIGN3SDP

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

For T5, `original` and `circle` reference the same Circle. The original ID and radius are saved before execution. Only the renderer reference is replaced. The check computes `original==circle`, compares the saved state to the state after replacement, and compares both actual renderings to fixed expected text.

Every check calls application code. The common `check(...)` method compares actual and expected strings, calculates PASS/FAIL, counts all checks, and prints the expected value for a failure. A failed comparison does not stop later checks.

## Bridge design

The two dimensions are shape behavior (`Circle`, `Square`) and output format (`VectorRenderer`, `RasterRenderer`, `AsciiRenderer`). `Shape` holds a constructor-injected `Renderer`; each subclass supplies its own retained shape data through `render(...)`. Shape classes never construct, cast to, or inspect concrete renderers. There are no combination subclasses.

One call: `Circle.execute()` -> `Shape.render("circle","radius",radius)` -> the selected `Renderer.render(...)` implementation.

`id`, `radius`, and `side` are private final fields. The renderer reference is private and can change only through the constructor or `setImplementation(...)`.

The matching UML is in `report.pdf` and `uml.svg`. Its editable UML class-diagram source is `uml.dot`, rendered with Graphviz. Regeneration is optional and is not part of the Java build:

```sh
neato -n2 -Tsvg uml.dot -o uml.svg
```

## Git evidence

The local history is included in `.git/`; keep that hidden folder when publishing the project. No GitHub repository was created or modified.


`extension.diff` was captured after committing I3 using:

```sh
git diff 46fa51ea04893118e337dd3806e101e989689357 HEAD -- src > extension.diff
```

## Publish and submit

Create an empty GitHub repository named `SыE2528ASSIGN3SDP` under `PactusOs`, without adding an initial README. The URL above is the intended destination, not a claim that publication has already happened.
