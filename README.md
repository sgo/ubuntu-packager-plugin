ubuntu-packager-plugin
======================

A basic Ubuntu/Debian packager plugin for [Gradle](http://gradle.org).
It has some quirks but it should be possible to use it to create packages.

Restriction
-----------

This plugin may only be used to build architecture independent packages, such as packages containing compiled Java code.

Example Project
---------------

The below examples come from the example project [helloworld-example](https://github.com/sgo/helloworld-example)

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
        archive = new File("helloworld.tar.gz").toURI()
        releaseNotes = "Example for the ubuntu packager plugin"
        author = 'me'
        email = 'me@example.org'
        homepage = 'http://example.org'
        depends {
            // uncomment to add dependencies to be installed
            // on 'mysql-server'
            // on 'jetty'
        }
        dirs {
            // you can add as many dir statements as you need
            dir '/usr/share/helloworld/bin'
        }
    
    }

dir structure
-------------

    helloworld-example
    ├── build.gradle
    ├── helloworld.tar.gz
    └── src
        └── ubuntu
            └── debian
                ├── copyright
                └── helloworld-example.install

The following optional scripts can be added to `src/ubuntu/debian`

* helloworld-example.default -- /etc/default/helloworld-example `holds environment variable defaults`
* helloworld-example.init -- /etc/init.d/helloworld-example `system start-stop script`
* helloworld-example.preinst -- `executed before installation`
* helloworld-example.postinst -- `executed after installation`
* helloworld-example.prerm -- `executed before removal`
* helloworld-example.postrm -- `executed after removal`

Finally you can still add any number of custom files to `src/ubuntu/overrides` which are copied to the same level as the contents of the tgz so they are available for use in `helloworld-example.install`

See the [packaging guide](https://wiki.ubuntu.com/PackagingGuide/HandsOn) as for the format to use in these files.

Usage
-----

    ~/helloworld-example$ gradle clean deb

Quirks
------

The plugin expects the presence of a tgz which isn't common for Java projects though not hard to make.

The package will be named based on the gradle name property which is the same as the directory in which the gradle project is located. As a result when using a CI system such as jenkins this name/dir will be called workspace and is probably not what you want. To work around this I have been putting my package scripts in their own subdir named the way I want the package to be named.

E.g:

* myapp/grails
* myapp/myapp
* myapp/myapp-data
