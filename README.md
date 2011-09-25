ubuntu-packager-plugin
======================

A basic Ubuntu/Debian packager plugin for Gradle.
It has some quirks but it should be possible to use it to create packages.

build.gradle example
--------------------

    buildscript {
        repositories {
            mavenRepo urls:'http://yourrepository.com'
        }
        dependencies {
            classpath "be.thinkerit.gradle:ubuntu-packager-plugin:0.1"
        }
    }
    
    apply plugin:'ubuntu'
    
    version = '0.1'
    
    ubuntu {
        archive = "/path/tp/package/mypackage.tar.gz"
        releaseNotes = "something interesting"
        author = 'me'
        email = 'me@example.org'
        homepage = 'http://example.org'
        depends {
            on 'mysql-server'
            on 'jetty'
        }
        dirs {
            dir '/usr/share/myapp/webapps'
            dir '/etc/myapp'
            dir '/var/log/myapp'
            dir '/var/cache/myapp/tmp'
            dir '/var/cache/myapp/data'
        }
    }

dir structure
-------------

* src/ubuntu/debian
* src/ubuntu/debian/copyright
* src/ubuntu/debian/myapp.default
* src/ubuntu/debian/myapp.init
* src/ubuntu/debian/myapp.install
* src/ubuntu/debian/myapp.postinst
* src/ubuntu/debian/myapp.postrm
* src/ubuntu/overrides

See the packaging guide as for the format to use in these files -- https://wiki.ubuntu.com/PackagingGuide/HandsOn

You can use src/ubuntu/overrides to provide any number of custom files which will be copied to the same location as the extracted tar and will be available to you in myapp.install to define where they should go on the filesystem.

Usage
-----

    ~/myapp$ gradle clean deb

Quirks
------

The plugin expects the presence of a gziped tar which isn't common for Java projects though not hard to make.

The package will be named based on the gradle name property which is the same as the directory in which the gradle project is located. As a result when using a ci system such as jenkins this name/dir will be called workspace and is probably not what you want. To work around this I have been putting my package scripts in their own subdir named the way I want the package to be named.

E.g:
* myapp/grails
* myapp/myapp
* myapp/myapp-data

So far the plugin will create debian packages based on the architecture on which the package is build. As I intend to use this for Java projects this should be changed to all instead in a future version.

