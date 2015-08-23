## Coffee Cats

[![Workflow](https://badge.waffle.io/non/cats.png?label=ready&title=Ready)](https://waffle.io/agenovese/coffee-cats)
[![Chat](https://badges.gitter.im/Join%20Chat.svg)](https://gitter.im/agenovese/coffee-cats)
[![codecov.io](http://codecov.io/github/agenovese/coffee-cats/coverage.svg?branch=master)](http://codecov.io/github/agenovese/coffee-cats?branch=master)

### Overview

Coffee Cats is an attempt to port Cats from Scala to Java,
it is primarily intended as an exploration of how far we can
push the Java type system, and as a learning tool for better
understanding functional programming structures.

Cats is a proof-of-concept library intended to provide abstractions
for functional programming in Scala.

The name is a concatenation of a playful translation of Java into Coffee
and cats a shortening of category.

### Getting Started

At the moment, coffee-cats is not published to any maven repository,
and it is not recommended for use in any real projects.

### Documentation

Work on Documentation hasn't started, in the meantime we suggest
looking at the documentation for cats

Cats documentation is available in the form of tutorials on the Cats
[website](http://non.github.io/cats/), as well as through
[Scaladoc](http://non.github.io/cats/api/#package) (also reachable through
the website).

### Building Coffee Cats

Coffee Cats is a standard Maven Project.
You'll need [Maven](http://maven.apache.org) and a 
[Java 8 JDK](http://www.oracle.com/technetwork/java/javase/downloads/index.html) installed in order to build Coffee Cats

Run `mvn package` from the top level project directory to build coffee-cats, execute the tests, and produce a jar file. 

### Modules

It's my intent to modularize coffee cats at a future date, at the moment it is a single module project

### How can I contribute to Coffee Cats?

There are many ways to support Coffee Cats' development:

 * Contribute to Cats: This project is a work in progress port 
   of the [cats](https://github.com/non/cats) library, contributions 
   to cats will eventually help this project as well. 

 * Port examples, tutorials, documentation, or functionality from cats.
   Please create an issue (if one doesn't already exist) and comment on 
   the it to help avoid duplicated work.

 * Fix bugs: Despite out best efforts bugs happen. Reporting problems you
   encounter (with the documentation, code, or anything else) helps us
   to improve. Look for issues labelled "ready" as good targets, but
   **please add a comment to the issue** if you start working on one.
   We want to avoid any duplicated effort.

 * Write JavaDoc comments: One of our goals is to have JavaDoc
   comments for all types in Cats. The documentation should describe
   the type and give a basic usage (it may also link to relevant
   papers).

 * Write tutorials and examples: In addition to inline JavaDoc
   comments, we hope to provide Markdown-based tutorials which can
   demonstrate how to use all the provided types. These should be
   *literate programs* i.e. narrative text interspersed with code.

 * Improve the laws and tests: Cats' type classes rely on laws (and
   law-checking) to make type classes useful and reliable. If you
   notice laws or tests which are missing (or could be improved)
   you can open an issue (or send a pull request).

 * Help with code review: Most of our design decisions are made
   through conversations on issues and pull requests. You can
   participate in these conversations to help guide the future of
   Coffee Cats.

   We will be using the **meta** label for large design decisions, and
   your input on these is especially appreciated.

 * Ask questions: we are hoping to make Coffee Cats (and functional
   programming in Java) as accessible as possible to the largest number of
   people. If you have questions it is likely many other people do as
   well, and as a community this is how we can grow and improve.

### Maintainers

The current maintainers (people who can merge pull requests) are:

 * [agenovese](https://github.com/agenovese) Angelo Genovese

We are currently following a practice of requiring at least one
sign-off to merge PRs. As the team grows, we will expand this to
two sign-offs.

### Contributing

Discussion around Coffee-Cats is currently happening in the
[Gitter channel](https://gitter.im/non/cats) as well as on Github
issue and PR pages. You can get an overview of who is working on what
via [Waffle.io](https://waffle.io/agenovese/coffee-cats).

Feel free to open an issue if you notice a bug, have an idea for a
feature, or have a question about the code. Pull requests are also
gladly accepted. For more information, check out the
[contributor guide](CONTRIBUTING.md). You can also see a list of past
contributors in [AUTHORS.md](AUTHORS.md).

People are expected to follow the
[Typelevel Code of Conduct](http://typelevel.org/conduct.html) when
discussing Cats on the Github page, Gitter channel, or other venues.

We hope that our community will be respectful, helpful, and kind. If
you find yourself embroiled in a situation that becomes heated, or
that fails to live up to our expectations, you should disengage and
contact one of the [project maintainers](#maintainers) in private. We
hope to avoid letting minor aggressions and misunderstandings escalate
into larger problems.

If you are being harassed, please contact one of [us](#maintainers)
immediately so that we can support you.

### Related Projects

Coffee Cats is a port of the Cats library [Cats](https://github.com/non/cats)
Cats is closely-related to [Structures](https://github.com/mpilquist/Structures);
both projects are descended from [Scalaz](https://github.com/scalaz/scalaz).

There are many related Haskell libraries, for example:

 * [semigroupoids](https://hackage.haskell.org/package/semigroupoids)
 * [profunctors](https://github.com/ekmett/profunctors)
 * [contravariant](https://github.com/ekmett/contravariant)
 * ...and so on.

### Copyright and License

All code is available to you under the MIT license, available at
http://opensource.org/licenses/mit-license.php and also in the
[COPYING](COPYING) file. The design is informed by many other
projects, in particular Cats, Structures, & Scalaz.

Copyright the maintainers, 2015.
