/*
 * Copyright 2010 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package packager.commands

final class Downloader {

    private final URI from
    private final File to

    Downloader(URI from, File to) {
        this.from = from
        this.to = to
    }

    void execute() {
        def dir = to.parentFile
        dir.mkdirs()

        def file = new File(dir, to.name)
        if(file.exists()) file.delete()
//        file.withWriter {it << from.toURL().openStream().bytes}
        file << from.toURL().openStream()
    }

    boolean equals(o) {
        if(this.is(o)) return true;
        if(getClass() != o.class) return false;

        Downloader that = (Downloader) o;

        if(from != that.from) return false;
        if(to != that.to) return false;

        return true;
    }

    int hashCode() {
        int result;
        result = (from != null ? from.hashCode() : 0);
        result = 31 * result + (to != null ? to.hashCode() : 0);
        return result;
    }


    public String toString() {
        return "Downloader{" +
                "from=" + from +
                ", to=" + to +
                '}';
    }
}
