#!/usr/bin/env sh
set -eu
GRADLE_VERSION="8.7"
BASE_DIR="${HOME}/.gradle/la-bible-du-foot/gradle-${GRADLE_VERSION}"
if [ ! -x "${BASE_DIR}/bin/gradle" ]; then
  mkdir -p "$(dirname "${BASE_DIR}")"
  TMP_ZIP="$(mktemp -t gradle.XXXXXX.zip)"
  curl -fsSL "https://services.gradle.org/distributions/gradle-${GRADLE_VERSION}-bin.zip" -o "${TMP_ZIP}"
  unzip -q "${TMP_ZIP}" -d "$(dirname "${BASE_DIR}")"
  rm -f "${TMP_ZIP}"
fi
exec "${BASE_DIR}/bin/gradle" "$@"
