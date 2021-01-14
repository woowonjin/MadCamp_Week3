from rest_framework import status
from rest_framework.decorators import api_view
from rest_framework.response import Response
from django.views.decorators.csrf import csrf_exempt
from . import models as user_models
import json

# Create your views here.
@csrf_exempt
@api_view(["POST"])
def login(request):
    if request.method == "POST":
        params_json = request.body.decode(encoding = "utf-8")
        data_json = json.loads(request.body)
        name = data_json["name"]
        uid = data_json["uid"]
        try:
            user = user_models.User.objects.get(uid=uid)
            return Response("{Result:Exists}")
        except:
            user = user_models.User.objects.create(uid=uid, username=name)
            user.save()
            return Response("{Result:Create Success}")