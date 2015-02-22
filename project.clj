(defproject whack-a-proxy "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}

  :source-paths ["src/clj" "src/cljs"]

  :dependencies [[org.clojure/clojure "1.6.0"]
                 [reagent "0.5.0-alpha3"]
                 [org.clojure/clojurescript "0.0-2850" :scope "provided"]
                 [environ "1.0.0"]]

  :plugins [[lein-cljsbuild "1.0.4"]
            [lein-environ "1.0.0"]]

  :min-lein-version "2.5.0"

  :clean-targets ^{:protect false} ["extension/js/compiled"]

  :cljsbuild {:builds {:app {:source-paths ["src/cljs"]
                             :compiler {:output-to     "extension/js/compiled/app.js"
                                        :output-dir    "extension/js/compiled/deps"
                                        ;;:externs       ["react/externs/react.js"]
                                        :asset-path   "js/out"
                                        :optimizations :none
                                        :pretty-print  true}}}}

  :profiles {:dev {:repl-options {:nrepl-middleware [cemerick.piggieback/wrap-cljs-repl]}

                   :dependencies [[leiningen "2.5.1"]
                                  [weasel "0.6.0-SNAPSHOT"]
                                  [com.cemerick/piggieback "0.1.6-SNAPSHOT"]
                                  [pjstadig/humane-test-output "0.6.0"]]

                   :plugins [[com.cemerick/clojurescript.test "0.3.3"]]

                   :injections [(require 'pjstadig.humane-test-output)
                                (pjstadig.humane-test-output/activate!)]

                   :env {:dev? true}

                   :cljsbuild {:builds {:app {:source-paths ["env/dev/cljs"]
                                              :compiler {:source-map true}}
                                        :test {:source-paths ["src/cljs"  "test/cljs"]
                                               :compiler {:output-to "target/test.js"
                                                          :optimizations :whitespace
                                                          :pretty-print true}}}
                               :test-commands {"unit" ["phantomjs" :runner
                                                       "test/vendor/es5-shim.js"
                                                       "test/vendor/es5-sham.js"
                                                       "test/vendor/console-polyfill.js"
                                                       "target/test.js"]}}}

             :production {:env {:production true}
                          :cljsbuild {:jar true
                                      :builds {:app
                                               {:source-paths ["env/prod/cljs"]
                                                :compiler
                                                {:optimizations :advanced
                                                 :pretty-print false}}}}}
             })
