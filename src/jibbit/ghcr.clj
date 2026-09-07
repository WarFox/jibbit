(ns jibbit.ghcr)

;; GHCR auth is HTTP Basic username:token. There's no CLI/SDK to shell out to,
;; so we read GitHub's documented env vars: GITHUB_ACTOR (username) and
;; GITHUB_TOKEN, falling back to CR_PAT for the local personal-access-token case.
(defn creds [getenv]
  (let [username (getenv "GITHUB_ACTOR")
        token    (or (getenv "GITHUB_TOKEN") (getenv "CR_PAT"))]
    (when-not (and username token)
      (throw (ex-info "GHCR authorizer needs GITHUB_ACTOR and GITHUB_TOKEN (or CR_PAT)" {})))
    {:username username
     :password token}))

(defn authorizer [_]
  (creds #(System/getenv %)))
