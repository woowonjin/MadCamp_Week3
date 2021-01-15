from rest_framework import status
from rest_framework.decorators import api_view
from rest_framework.response import Response
from django.views.decorators.csrf import csrf_exempt
from users import models as user_models
from diaries import models as diary_models
from . import models as like_models
import json
# Create your views here.

@csrf_exempt
@api_view(["POST"])
def like(request):
    if(request.method == "POST"):
        params_json = request.body.decode(encoding = "utf-8")
        data_json = json.loads(request.body)
        uid = data_json["uid"]
        pk = data_json["post"]
        try:
            user = user_models.User.objects.get(uid=uid)
            diary = diary_models.Diary.objects.get(pk=pk)
            try:
                like = like_models.Like.objects.get(user=user, diary=diary)
                #should delete like
                like.delete()
                return Response("Delete")
            except:
                # should make like
                like = like_models.Like.objects.create(user=user, diary=diary)
                return Response("Create")
        except:
            return Response("{Result:Error}")
    else:
        return Response("{Result:Error}")