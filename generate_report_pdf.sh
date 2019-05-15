#!/usr/bin/env sh

# Simple Linux shell script to generate a PDF version of the report. Requires Pandoc.
pandoc doc/report.md -o build/report.pdf
