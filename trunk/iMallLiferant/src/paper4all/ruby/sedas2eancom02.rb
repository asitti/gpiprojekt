#!/usr/bin/env jruby
#
# This file was generated by RubyGems.
#
# The application 'edi4r' is installed as part of a gem, and
# this file is here to facilitate running it.
#

require 'rubygems'

version = ">= 0"

if ARGV.first =~ /^_(.*)_$/ and Gem::Version.correct? $1 then
  version = $1
  ARGV.shift
end

gem 'edi4r', version
load 'sedas2eancom02.rb'
