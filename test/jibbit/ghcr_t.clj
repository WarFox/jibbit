(ns jibbit.ghcr-t
  (:require [clojure.test :as t]
            [jibbit.ghcr :refer [creds]]))

(t/deftest creds-tests
  (t/is (= {:username "octocat" :password "tok"}
           (creds {"GITHUB_ACTOR" "octocat" "GITHUB_TOKEN" "tok"})))
  (t/is (= {:username "octocat" :password "pat"}
           (creds {"GITHUB_ACTOR" "octocat" "CR_PAT" "pat" "GITHUB_TOKEN" "tok"})))
  (t/is (= {:username "octocat" :password "tok"}
           (creds {"GITHUB_ACTOR" "octocat" "CR_PAT" "  " "GITHUB_TOKEN" "tok"})))
  (t/is (thrown? Throwable (creds {"GITHUB_ACTOR" "octocat"})))
  (t/is (thrown? Throwable (creds {"GITHUB_TOKEN" "tok"})))
  (t/is (thrown? Throwable (creds {"GITHUB_ACTOR" "" "GITHUB_TOKEN" "tok"})))
  (t/is (thrown? Throwable (creds {"GITHUB_ACTOR" "octocat" "CR_PAT" "" "GITHUB_TOKEN" " "}))))
