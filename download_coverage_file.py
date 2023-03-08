# %%
import subprocess
with open('result.txt') as f:
    for line in f:
        if "https://console.developers.google.com/storage/browser/" in line:
            url_start_index = line.index('[') + 1
            url_end_index = line.index(']')
            url = line[url_start_index:url_end_index]
            gs_uri = url.replace(
                "https://console.developers.google.com/storage/browser/", "gs://")
coverage_uri = subprocess.check_output(
    "gsutil ls -r %s | grep coverage.ec" % gs_uri, shell=True).decode('utf-8').rstrip()
subprocess.run(["gsutil", "cp", coverage_uri, "."], check=True)
# %%
