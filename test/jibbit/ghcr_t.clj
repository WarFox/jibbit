(ns jibbit.ghcr-t
  (:require [clojure.test :as t]
            [jibbit.ghcr :refer [creds]]))

(t/deftest creds-tests
  (t/is (= {:username "octocat" :password "tok"}
           (creds {"GITHUB_ACTOR" "octocat" "GITHUB_TOKEN" "tok"})))
  (t/is (= {:username "octocat" :password "pat"}
           (creds {"GITHUB_ACTOR" "octocat" "CR_PAT" "pat"})))
  (t/is (= {:username "octocat" :password "pat"}
           (creds {"GITHUB_ACTOR" "octocat" "CR_PAT" "pat" "GITHUB_TOKEN" "tok"}))
        "CR_PAT wins over GITHUB_TOKEN")
  (t/is (= {:username "octocat" :password "tok"}
           (creds {"GITHUB_ACTOR" "octocat" "CR_PAT" "" "GITHUB_TOKEN" "tok"}))
        "empty CR_PAT falls back to GITHUB_TOKEN")
  (t/is (= {:username "octocat" :password "tok"}
           (creds {"GITHUB_ACTOR" "octocat" "CR_PAT" "  " "GITHUB_TOKEN" "tok"}))
        "whitespace CR_PAT falls back to GITHUB_TOKEN")
  (t/is (thrown? Throwable (creds {}))
        "throws when env is empty")
  (t/is (thrown? Throwable (creds {"GITHUB_ACTOR" "octocat"}))
        "throws when token is missing")
  (t/is (thrown? Throwable (creds {"GITHUB_TOKEN" "tok"}))
        "throws when actor is missing")
  (t/is (thrown? Throwable (creds {"GITHUB_ACTOR" "" "GITHUB_TOKEN" "tok"}))
        "throws when actor is blank")
  (t/is (thrown? Throwable (creds {"GITHUB_ACTOR" "octocat" "CR_PAT" "" "GITHUB_TOKEN" " "}))
        "throws when tokens are blank"))
