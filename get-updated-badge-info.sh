stages:
    - build

build_badges:
  stage: build
  tags:
    - docker-build
  before_script:
    - chmod +x get-updated-badge-info.sh
  script:
    - echo "Some script to build your code"
  after_script:
    - ./get-updated-badge-info.sh
  artifacts:
    paths:
        - badges.json
    when: always
    expire_in: 4 weeks
