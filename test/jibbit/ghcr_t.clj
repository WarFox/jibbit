(ns jibbit.ghcr-t
  (:require [clojure.test :as t]
            [jibbit.ghcr :refer [creds]]))

(t/deftest creds-tests
  (t/is (= {:username "octocat" :password "tok"}
           (creds {"GITHUB_ACTOR" "octocat" "GITHUB_TOKEN" "tok"})))
  (t/is (= {:username "octocat" :password "pat"}
           (creds {"GITHUB_ACTOR" "octocat" "CR_PAT" "pat"}))
        "CR_PAT is the fallback token")
  (t/is (thrown? Throwable (creds {}))
        "throws when env is empty")
  (t/is (thrown? Throwable (creds {"GITHUB_ACTOR" "octocat"}))
        "throws when token is missing"))
