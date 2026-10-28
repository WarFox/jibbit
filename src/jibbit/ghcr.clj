(ns jibbit.ghcr
  (:require [clojure.string :as str]))

;; GHCR auth is HTTP Basic username:token. There's no CLI/SDK to shell out to,
;; so we read GitHub's documented env vars: GITHUB_ACTOR (username) and
;; CR_PAT (personal access token), falling back to GITHUB_TOKEN for Actions.
;; Blank values count as unset, e.g. an empty ${{ secrets.MISSING }}.
(defn creds [getenv]
  (let [env      #(let [v (getenv %)] (when-not (str/blank? v) v))
        username (env "GITHUB_ACTOR")
        token    (or (env "CR_PAT") (env "GITHUB_TOKEN"))]
    (when-not (and username token)
      (throw (ex-info "GHCR authorizer needs non-blank GITHUB_ACTOR and CR_PAT or GITHUB_TOKEN" {})))
    {:username username
     :password token}))

(defn authorizer [_]
  (creds #(System/getenv %)))
