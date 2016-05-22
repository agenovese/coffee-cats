---
layout: default
title:  "Contributing"
section: "contributing"
---
# Contributor guide

Discussion around Coffee-Cats is currently happening in the 
[Gitter channel](https://gitter.im/agenovese/coffee-cats) as well as on Github
issue and PR pages. You can get an overview of who is working on what
via [Waffle.io](https://waffle.io/agenovese/coffee-cats).

Feel free to open an issue if you notice a bug, have an idea for a
feature, or have a question about the code. Pull requests are also
gladly accepted. For more information, check out the [contributor guide](CONTRIBUTING.md).

People are expected to follow the
[Typelevel Code of Conduct](http://typelevel.org/conduct.html) when
discussing Coffee-Cats on the Github page, Gitter channel, or other venues.

We hope that our community will be respectful, helpful, and kind. If
you find yourself embroiled in a situation that becomes heated, or
that fails to live up to our expectations, you should disengage and
contact one of the [project maintainers](#maintainers) in private. We
hope to avoid letting minor aggressions and misunderstandings escalate
into larger problems.

If you are being harassed, please contact one of [us](#maintainers)
immediately so that we can support you.

## How can I help?

Coffee-Cats follows a standard
[fork and pull](https://help.github.com/articles/using-pull-requests/)
model for contributions via GitHub pull requests.

Below is a list of the steps that might be involved in an ideal
contribution. If you don't have the time to go through every step,
contribute what you can, and someone else will probably be happy to
follow up with any polishing that may need to be done.

If you want to touch up some documentation or fix typos, feel free to
skip these steps and jump straight to submitting a pull request.

 1. [Find something that belongs in coffee-cats](#find-something-that-belongs-in-coffee-cats)
 2. [Let us know you are working on it](#let-us-know-you-are-working-on-it)
 3. [Implement your contribution](#write-code)
 4. [Write tests](#write-tests)
 5. [Write documentation](#write-documentation)
 6. [Write examples](#write-examples)
 7. [Submit pull request](#submit-a-pull-request)

### Find something that belongs in coffee cats

Looking for a way that you can help out? Check out our
[Waffle.io](https://waffle.io/agenovese/coffee-cats). Choose a card from the
"Ready" column. Before you start working on it, make sure that it's
not already assigned to someone and that nobody has left a comment
saying that they are working on it!

(Of course, you can also comment on an issue someone is already
working on and offer to collaborate.)

Have an idea for something new? That's great! We recommend that you
make sure it belongs in cats before you put effort into creating a
pull request. The preferred ways to do that are to either:

 * [create a GitHub issue](https://github.com/agenovese/coffee-cats/issues/new)
   describing your idea.
 * get feedback in the [cats Gitter room](https://gitter.im/agenovese/coffee-cats).

Things that belong in cats generally have the following characteristics:

 * Their behavior is governed by well-defined [laws](laws).
 * They provide general abstractions.

Laws help keep types consistent, and remove ambiguity or sensitivity
about how particular instances can behave. We've found that types with
laws are often more useful than *lawless* types

(In some cases, *lawless* type classes and instances are useful. We
intend to support some of these in a future module.)

By staying general, Coffee Cats' abstractions are widely-applicable, and not
tied to particular libraries or strategies. Rather than being a
library to work with databases, HTTP requests, etc, Coffee Cats provides
abstractions used to build those libraries.

Coffee Cats is intended to be lean and modular.
Some great ideas are not a great fit, either due to their size or
their complexity. In these cases, creating your own library that
depends on Coffee Cats is probably the best plan.

### Let us know you are working on it

If there is already a GitHub issue for the task you are working on,
leave a comment to let people know that you are working on it. If
there isn't already an issue and it is a non-trivial task, it's a good
idea to create one (and note that you're working on it). This prevents
contributors from duplicating effort.

### Write code

TODO

### Write tests

TODO

## Contributing documentation

TODO

### Write examples

TODO

### Submit a pull request

Before you open a pull request, you should make sure that `mvn
validate` runs successfully.

If your pull request addresses an existing issue, please tag that
issue number in the body of your pull request and commit message. For
example, if your pull request addresses issue number 52, please
include "fixes #52".

If you make changes after you have opened your pull request, 
please add them as separate commits and avoid squashing or 
rebasing. Squashing and rebasing can lead to a tidier git history, 
but they can also be a hassle if somebody else has 
done work based on your branch.

## How did we do?

Getting involved in an open source project can be tough. As a
newcomer, you may not be familiar with coding style conventions,
project layout, release cycles, etc. This document seeks to demystify
the contribution process for the coffee-cats project.

It may take a while to familiarize yourself with this document, but if
we are doing our job right, you shouldn't have to spend months poring
over the project source code or lurking the [Gitter room](https://gitter.im/agenovese/coffee-cats) 
before you feel comfortable
contributing. In fact, if you encounter any confusion or frustration
during the contribution process, please create a GitHub issue and
we'll do our best to improve the process.
