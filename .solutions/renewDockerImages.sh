#!/bin/bash

# This script will renew all Novatec-provided Docker images as used in the CPJ.
#
# Preparation:
# - apply code changes
# - update any standard v0.1 JARs
# - commit
# - rebase all feature branches onto the new master:
# -- git checkout todobackend_v0.2 && git rebase master
# -- git checkout todoui_v0.2 && git rebase master
# -- git checkout todoui_v0.3 && git rebase todoui_v0.2
# - then execute this script
# - examine/test the resulting Docker images and if content push them to Docker hub

BUILDCMD="mvn clean package --batch-mode -DskipTests" # we can safely skip tests as they should have run during the standard v0.1 JAR build
RESETCMD="git reset --hard" # how to reset our checkout to a sane state
OCELOTCMD="curl -sSL https://github.com/inspectIT/inspectit-ocelot/releases/download/1.6.1/inspectit-ocelot-agent-1.6.1.jar -o inspectit-ocelot-agent-1.6.1.jar"

set -xeu

# start out in a well-defined directory from the current branch with everything in place that we will later need
SCRIPT="$(readlink -f "$0")"
CHECKOUTDIR="$(dirname "$SCRIPT")/.."
GITBRANCH="$(git branch --show-current)"
cd "$CHECKOUTDIR"
$OCELOTCMD


# -----
# hello
# -----

# build
cd "$CHECKOUTDIR/quarkus-hello/"
./mvnw package -Pnative # as per the Dockerfile.native from next line

# dockerize
cd "$CHECKOUTDIR/quarkus-hello/"
docker build -f src/main/docker/Dockerfile.native -t novatec/technologyconsulting-hello-container:v0.1 .

# cleanup
$RESETCMD


# ----------------
# todobackend v0.1
# ----------------

# build
#cd "$CHECKOUTDIR/todobackend/"
#$BUILDCMD # build should already have happened as per Preparation above

# dockerize
cd "$CHECKOUTDIR"
docker build -f .solutions/Dockerfile-todobackend        -t novatec/technologyconsulting-containerexerciseapp-todobackend:v0.1 .

# cleanup
$RESETCMD

# ---------------------------
# todobackend v0.2 (+ ocelot)
# ---------------------------
git checkout todobackend_v0.2

# build
cd "$CHECKOUTDIR/todobackend/"
$BUILDCMD

# dockerize
cd "$CHECKOUTDIR"
docker build -f .solutions/Dockerfile-todobackend        -t novatec/technologyconsulting-containerexerciseapp-todobackend:v0.2 .
docker build -f .solutions/Dockerfile-todobackend-ocelot -t novatec/technologyconsulting-containerexerciseapp-todobackend:v0.2-ocelot .

# cleanup
$RESETCMD


# -----------
# todoui v0.1
# -----------

# build
#cd "$CHECKOUTDIR/todoui/"
#$BUILDCMD # build should already have happened as per Preparation above

# dockerize
cd "$CHECKOUTDIR"
docker build -f .solutions/Dockerfile-todoui        -t novatec/technologyconsulting-containerexerciseapp-todoui:v0.1 .

# cleanup
$RESETCMD

# -----------
# todoui v0.2
# -----------
git checkout todoui_v0.2

# build
cd "$CHECKOUTDIR/todoui/"
$BUILDCMD

# dockerize
cd "$CHECKOUTDIR"
docker build -f .solutions/Dockerfile-todoui        -t novatec/technologyconsulting-containerexerciseapp-todoui:v0.2 .

# cleanup
$RESETCMD

# ----------------------
# todoui v0.3 (+ ocelot)
# ----------------------
git checkout todoui_v0.3

# build
cd "$CHECKOUTDIR/todoui/"
$BUILDCMD

# dockerize
cd "$CHECKOUTDIR"
docker build -f .solutions/Dockerfile-todoui        -t novatec/technologyconsulting-containerexerciseapp-todoui:v0.3 .
docker build -f .solutions/Dockerfile-todoui-ocelot -t novatec/technologyconsulting-containerexerciseapp-todoui:v0.3-ocelot .

# cleanup
$RESETCMD


# final cleanup
git checkout "$GITBRANCH"
